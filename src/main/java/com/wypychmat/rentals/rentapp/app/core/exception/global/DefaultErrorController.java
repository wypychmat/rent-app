package com.wypychmat.rentals.rentapp.app.core.exception.global;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wypychmat.rentals.rentapp.app.core.util.ApiVersion;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.wypychmat.rentals.rentapp.app.core.util.Constant.DATA_PATTERN;
import static com.wypychmat.rentals.rentapp.app.core.util.Constant.JSON_CONTENT;

@Controller
class DefaultErrorController extends AbstractErrorController {

    private static final String PATH = "/error";

    DefaultErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping(value = PATH, consumes = {"*/*"}, produces = ApiVersion.JSON)
    public void error(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpStatus httpStatus = getStatus(request);
        response.addHeader(HttpHeaders.CONTENT_TYPE, JSON_CONTENT);
        response.setStatus(httpStatus.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String cause = httpStatus.getReasonPhrase();

        Map<String, Object> errorAttributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        if (request.getAttribute("customErrorMessage") != null)
            cause += ", " + request.getAttribute("customErrorMessage").toString();

        String path = errorAttributes.get("path").toString();
        new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .writeValue(response.getWriter(),
                        new GlobalException(httpStatus,
                                new SimpleDateFormat(DATA_PATTERN).format(new Date()),
                                cause,
                                path));
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}

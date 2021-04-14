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

import static com.wypychmat.rentals.rentapp.app.core.util.ApiVersion.*;
import static com.wypychmat.rentals.rentapp.app.core.util.Constant.*;

@Controller
class DefaultErrorController extends AbstractErrorController {

    private static final String PATH = "/error";

    DefaultErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping(value = PATH, consumes = ANY, produces = ANY)
    public void error(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpStatus httpStatus = getStatus(request);
        response.addHeader(HttpHeaders.CONTENT_TYPE, JSON_CONTENT);
        response.setStatus(httpStatus.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String cause = httpStatus.getReasonPhrase();

        Map<String, Object> errorAttributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        if (request.getAttribute(CUSTOM_ERROR_MESSAGE) != null)
            cause += ", " + request.getAttribute(CUSTOM_ERROR_MESSAGE).toString();

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

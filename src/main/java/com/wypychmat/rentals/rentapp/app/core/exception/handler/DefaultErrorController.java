package com.wypychmat.rentals.rentapp.app.core.exception.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
public class DefaultErrorController extends AbstractErrorController {

    private static final String PATH = "/error";

    public DefaultErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping(value = PATH)
    public void error(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpStatus status = getStatus(request);
        response.addHeader("Content-Type", "application/json");
        response.setStatus(status.value());

        Map<String, Object> errorAttributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        String path = errorAttributes.get("path").toString();
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        new ObjectMapper().writeValue(response.getWriter(), new ErrorResponse(status, path,
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())));
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    static class ErrorResponse {
        private String date;
        private int status;
        private String error;
        private String requestPath;


        public ErrorResponse(HttpStatus status, String requestPath, String date) {
            this.status = status.value();
            this.error = status.getReasonPhrase();
            this.requestPath = requestPath;
            this.date = date;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getRequestPath() {
            return requestPath;
        }

        public void setRequestPath(String requestPath) {
            this.requestPath = requestPath;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}

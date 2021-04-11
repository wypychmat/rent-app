package com.wypychmat.rentals.rentapp.app.core.exception.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.wypychmat.rentals.rentapp.app.core.util.Constant.DATA_PATTERN;
import static com.wypychmat.rentals.rentapp.app.core.util.Constant.JSON_CONTENT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

abstract class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final ObjectMapper objectMapper;

    GlobalExceptionHandler() {
        objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    String getErrorBody(HttpStatus status, String path) throws JsonProcessingException {
        return objectMapper.writeValueAsString(
                new GlobalException(status,
                        new SimpleDateFormat(DATA_PATTERN).format(new Date()),
                        status.getReasonPhrase(), path));
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        try {
            return customExceptionHandler(request, status);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    ResponseEntity<Object> customExceptionHandler(WebRequest request,
                                                  HttpStatus httpStatus) throws JsonProcessingException {

        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        String body = getErrorBody(httpStatus, path);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, JSON_CONTENT);
        return new ResponseEntity<>(body, httpHeaders, httpStatus);
    }
}

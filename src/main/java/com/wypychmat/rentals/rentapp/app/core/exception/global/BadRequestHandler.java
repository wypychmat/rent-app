package com.wypychmat.rentals.rentapp.app.core.exception.global;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import static com.wypychmat.rentals.rentapp.app.core.util.Constant.JSON_CONTENT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@ControllerAdvice
class BadRequestHandler extends GlobalExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        try {
            return customHandler(ex, request,status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

    private ResponseEntity<Object> customHandler(HttpMessageNotReadableException ex,
                                                 WebRequest request,
                                                 HttpStatus httpStatus) throws JsonProcessingException {

        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        String body = getErrorBody(httpStatus, path);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, JSON_CONTENT);
        return handleExceptionInternal(ex, body, httpHeaders, httpStatus, request);
    }

}

package com.wypychmat.rentals.rentapp.app.core.exception.global;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
class GlobalExceptionHandlerImpl extends GlobalExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        try {
            return customExceptionHandler(request, status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

}

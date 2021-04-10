package com.wypychmat.rentals.rentapp.app.core.exception.register;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static com.wypychmat.rentals.rentapp.app.core.util.Constant.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@ControllerAdvice
class InvalidUserRequestExceptionHandler extends BasicExceptionHandler {

    @ExceptionHandler(value
            = {InvalidUserRequestException.class})
    ResponseEntity<Object> invalidRequestHandler(InvalidUserRequestException ex,
                                                           WebRequest request) throws JsonProcessingException {
        String body = getBody(new BasicErrorResponse(
                HttpStatus.CONFLICT,
                ex.getErrors()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, JSON_CONTENT);
        return handleExceptionInternal(ex, body, httpHeaders, HttpStatus.CONFLICT, request);
    }

    @Override
    Class<? extends RuntimeException> setView() {
        return InvalidUserRequestException.class;
    }
}

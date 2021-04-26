package com.wypychmat.rentals.rentapp.app.core.exception.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wypychmat.rentals.rentapp.app.core.exception.register.InvalidConfirmationTokenException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.wypychmat.rentals.rentapp.app.core.util.Constant.JSON_CONTENT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@ControllerAdvice
class InvalidPageRequestExceptionHandler extends ResponseEntityExceptionHandler {
    private final ObjectMapper objectMapper;

    InvalidPageRequestExceptionHandler() {
        objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @ExceptionHandler(value = {InvalidPageRequestException.class})
    ResponseEntity<Object> handleException(InvalidPageRequestException ex,
                                           WebRequest request) throws JsonProcessingException {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, JSON_CONTENT);
        return handleExceptionInternal(ex, "body", httpHeaders, HttpStatus.BAD_REQUEST, request);
    }


}

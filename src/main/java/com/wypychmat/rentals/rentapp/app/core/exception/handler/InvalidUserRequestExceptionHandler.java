package com.wypychmat.rentals.rentapp.app.core.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidUserRequestException;
import com.wypychmat.rentals.rentapp.app.core.exception.dto.BasicErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@ControllerAdvice
public class InvalidUserRequestExceptionHandler extends BasicExceptionHandler {

    @ExceptionHandler(value
            = {InvalidUserRequestException.class})
    protected ResponseEntity<Object> invalidRequestHandler(InvalidUserRequestException ex,
                                                           WebRequest request) throws JsonProcessingException {
        String body = getBody(new BasicErrorResponse(
                HttpStatus.CONFLICT,
                ex.getErrors()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, "application/json");
        return handleExceptionInternal(ex, body, httpHeaders, HttpStatus.CONFLICT, request);
    }

    @Override
    protected Class<? extends RuntimeException> setView() {
        return InvalidUserRequestException.class;
    }
}

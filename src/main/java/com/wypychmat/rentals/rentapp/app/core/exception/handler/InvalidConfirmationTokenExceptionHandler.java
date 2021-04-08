package com.wypychmat.rentals.rentapp.app.core.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidConfirmationTokenException;
import com.wypychmat.rentals.rentapp.app.core.dto.exception.BasicErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@ControllerAdvice
public class InvalidConfirmationTokenExceptionHandler extends BasicExceptionHandler {
    @ExceptionHandler(value = {InvalidConfirmationTokenException.class})
    protected ResponseEntity<Object> invalidRequestHandler(InvalidConfirmationTokenException ex,
                                                           WebRequest request) throws JsonProcessingException {
        String body = getBody(new BasicErrorResponse(
                ex.getHttpStatus(),
                ex.getMessage()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, "application/json");
        return handleExceptionInternal(ex, body, httpHeaders, ex.getHttpStatus(), request);
    }

    @Override
    protected Class<? extends RuntimeException> setView() {
        return InvalidConfirmationTokenException.class;
    }
}

package com.wypychmat.rentals.rentapp.app.core.exception.register;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static com.wypychmat.rentals.rentapp.app.core.util.Constant.JSON_CONTENT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@ControllerAdvice
class InvalidConfirmationTokenExceptionHandler extends BasicExceptionHandler {
    @ExceptionHandler(value = {InvalidConfirmationTokenException.class})
    ResponseEntity<Object> invalidRequestHandler(InvalidConfirmationTokenException ex,
                                                           WebRequest request) throws JsonProcessingException {
        String body = getBody(new BasicErrorResponse(
                ex.getHttpStatus(),
                ex.getMessage()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, JSON_CONTENT);
        return handleExceptionInternal(ex, body, httpHeaders, ex.getHttpStatus(), request);
    }

    @Override
    Class<? extends RuntimeException> setView() {
        return InvalidConfirmationTokenException.class;
    }
}

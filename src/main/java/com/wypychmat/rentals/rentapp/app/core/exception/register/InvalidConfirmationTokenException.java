package com.wypychmat.rentals.rentapp.app.core.exception.register;

import org.springframework.http.HttpStatus;

public class InvalidConfirmationTokenException extends RuntimeException {
    private final HttpStatus httpStatus;

    public InvalidConfirmationTokenException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

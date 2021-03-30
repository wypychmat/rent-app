package com.wypychmat.rentals.rentapp.app.core.exception;

import java.util.Map;

public class InvalidUserRequestException extends RuntimeException {
    private final Map<String,String> errors;

    public InvalidUserRequestException(String message, Map<String,String> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}

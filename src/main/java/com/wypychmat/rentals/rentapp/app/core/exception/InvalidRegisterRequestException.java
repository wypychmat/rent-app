package com.wypychmat.rentals.rentapp.app.core.exception;

import java.util.List;
import java.util.Map;

public class InvalidRegisterRequestException extends RuntimeException {
    private Map<String,String> errors;

    public InvalidRegisterRequestException(String message, Map<String,String> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}

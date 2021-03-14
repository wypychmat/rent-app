package com.wypychmat.rentals.rentapp.app.core.exception;

import java.util.List;

public class InvalidRegisterRequestException extends RuntimeException {
    private List<String> errors;

    public InvalidRegisterRequestException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}

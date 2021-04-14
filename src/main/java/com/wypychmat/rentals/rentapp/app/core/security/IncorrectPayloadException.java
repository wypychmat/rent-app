package com.wypychmat.rentals.rentapp.app.core.security;

import java.util.Map;

class IncorrectPayloadException extends Exception {

    private final Map<String, String> errors;

    public IncorrectPayloadException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}

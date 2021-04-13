package com.wypychmat.rentals.rentapp.app.core.security;

import org.springframework.security.core.AuthenticationException;

import java.util.HashMap;
import java.util.Map;

class BadRequestAuthException extends AuthenticationException {
    private final Map<String, String> errors;

    BadRequestAuthException(String msg) {
        super(msg);
        this.errors = new HashMap<>();
        errors.put("message", msg);
    }

    BadRequestAuthException(String msg, Map<String, String> errors) {
        super(msg);
        this.errors = errors;
    }

    Map<String, String> getErrors() {
        return errors;
    }


}

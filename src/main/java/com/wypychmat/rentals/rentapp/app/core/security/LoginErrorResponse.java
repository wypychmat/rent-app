package com.wypychmat.rentals.rentapp.app.core.security;

import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class LoginErrorResponse extends LoginResponse {
    private Map<String, String> errors;
    private String type;


    LoginErrorResponse(HttpStatus status, Date issuedAt, String message) {
        super(status, issuedAt);
        this.errors = new HashMap<>();
        errors.put("message", message);
    }

    LoginErrorResponse(HttpStatus status, Date issuedAt, Map<String, String> errors) {
        super(status, issuedAt);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

}

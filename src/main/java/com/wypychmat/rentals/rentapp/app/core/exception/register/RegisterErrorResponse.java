package com.wypychmat.rentals.rentapp.app.core.exception.register;

import com.fasterxml.jackson.annotation.JsonView;
import com.wypychmat.rentals.rentapp.app.core.exception.register.InvalidConfirmationTokenException;
import com.wypychmat.rentals.rentapp.app.core.exception.register.InvalidUserRequestException;
import org.springframework.http.HttpStatus;

import java.util.Map;

class RegisterErrorResponse {
    private int status;
    private String issuedAt;
    @JsonView({InvalidUserRequestException.class})
    private Map<String, String> errors;
    @JsonView({InvalidConfirmationTokenException.class})
    private String error;

    public RegisterErrorResponse(HttpStatus status, Map<String, String> errors) {
        this.status = status.value();
        this.errors = errors;
    }


    public RegisterErrorResponse(HttpStatus status, String error) {
        this.status = status.value();
        this.error = error;
    }

    public RegisterErrorResponse() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(String issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

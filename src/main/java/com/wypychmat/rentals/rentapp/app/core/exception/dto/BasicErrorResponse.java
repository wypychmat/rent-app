package com.wypychmat.rentals.rentapp.app.core.exception.dto;

import java.util.Map;

public class BasicErrorResponse {
    private int status;
    private String issuedAt;
    private Map<String,String> errors;

    public BasicErrorResponse(int status, String issuedAt, Map<String,String>  errors) {
        this.status = status;
        this.issuedAt = issuedAt;
        this.errors = errors;
    }

    public BasicErrorResponse() {
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
}

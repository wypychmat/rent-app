package com.wypychmat.rentals.rentapp.app.core.security;

import org.springframework.http.HttpStatus;

import java.util.Date;

abstract class LoginResponse {
    protected int status;
    protected String type;
    protected Date issuedAt;

    LoginResponse(HttpStatus httpStatus, Date issuedAt) {
        this.status = httpStatus.value();
        type = httpStatus.getReasonPhrase();
        this.issuedAt = issuedAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

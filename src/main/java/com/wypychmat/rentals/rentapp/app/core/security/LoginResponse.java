package com.wypychmat.rentals.rentapp.app.core.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Date;

abstract class LoginResponse {
    protected int status;
    protected Date issuedAt;

    LoginResponse(int status, Date issuedAt) {
        this.status = status;
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
}

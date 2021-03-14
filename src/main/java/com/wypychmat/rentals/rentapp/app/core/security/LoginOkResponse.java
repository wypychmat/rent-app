package com.wypychmat.rentals.rentapp.app.core.security;

import java.util.Date;

class LoginOkResponse extends LoginResponse {
    private Date expiredAt;
    private String token;

    public LoginOkResponse(int status, Date issuedAt, Date expiredAt, String token) {
        super(status, issuedAt);
        this.expiredAt = expiredAt;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }
}

package com.wypychmat.rentals.rentapp.app.core.security;

import java.util.Date;

class LoginErrorResponse extends LoginResponse {
    private String message;


    LoginErrorResponse(int status, Date issuedAt, String message) {
        super(status, issuedAt);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.wypychmat.rentals.rentapp.app.core.security;

import javax.validation.constraints.NotBlank;

class LoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    LoginRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

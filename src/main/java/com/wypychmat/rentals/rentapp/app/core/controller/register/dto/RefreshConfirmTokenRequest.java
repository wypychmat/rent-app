package com.wypychmat.rentals.rentapp.app.core.controller.register.dto;

import javax.validation.constraints.NotBlank;

public class RefreshConfirmTokenRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

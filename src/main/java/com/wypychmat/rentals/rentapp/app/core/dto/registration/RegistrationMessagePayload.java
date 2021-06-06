package com.wypychmat.rentals.rentapp.app.core.dto.registration;

public class RegistrationMessagePayload {
    private final String username;
    private final String email;
    private final String token;

    public RegistrationMessagePayload(String username, String email, String token) {
        this.username = username;
        this.email = email;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}

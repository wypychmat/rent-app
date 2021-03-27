package com.wypychmat.rentals.rentapp.app.core.service.user;

class RegistrationMessagePayload {
    private final String username;
    private final String email;
    private final String token;

    RegistrationMessagePayload(String username, String email, String token) {
        this.username = username;
        this.email = email;
        this.token = token;
    }

    String getUsername() {
        return username;
    }

    String getEmail() {
        return email;
    }

    String getToken() {
        return token;
    }
}

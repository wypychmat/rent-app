package com.wypychmat.rentals.rentapp.app.core.security;

class AdminOrModerator {
    private final String name;
    private final String password;

    AdminOrModerator(String getName, String password) {
        this.name = getName;
        this.password = password;
    }

    String getName() {
        return name;
    }

    String getPassword() {
        return password;
    }
}

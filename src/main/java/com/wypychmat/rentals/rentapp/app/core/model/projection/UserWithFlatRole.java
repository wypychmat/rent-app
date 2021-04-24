package com.wypychmat.rentals.rentapp.app.core.model.projection;

public class UserWithFlatRole {
    private final String username;
    private final String roles;
    private final String email;
    private final Boolean enabled;

    public UserWithFlatRole(String username, String roles, String email, Boolean enabled) {
        this.username = username;
        this.roles = roles;
        this.email = email;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public String getRoles() {
        return roles;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getEnabled() {
        return enabled;
    }
}

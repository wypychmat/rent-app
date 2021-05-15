package com.wypychmat.rentals.rentapp.app.core.dto.user;

import java.util.List;

public class UserDto {


    private Long id;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private boolean isEnabled;

    private List<String> roles;

    public UserDto() {
    }

    public UserDto(Long id, String username, String email, String firstName, String lastName, boolean isEnabled, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isEnabled = isEnabled;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public UserDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public UserDto setEnabled(boolean enabled) {
        isEnabled = enabled;
        return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public UserDto setRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }
}

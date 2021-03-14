package com.wypychmat.rentals.rentapp.app.core.controller.dto;


import com.wypychmat.rentals.rentapp.app.core.validation.ValidEmail;

public class RegistrationRequest {

    private String username;
    private String password;
    private String rePassword;
    @ValidEmail
    private String email;
    private String firstName;
    private String lastName;

    public RegistrationRequest() {
    }

    public RegistrationRequest(String username, String password, String rePassword, String email, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.rePassword = rePassword;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

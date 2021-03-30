package com.wypychmat.rentals.rentapp.app.core.controller.dto.request;


import com.wypychmat.rentals.rentapp.app.core.service.user.validation.PasswordConfirmation;
import com.wypychmat.rentals.rentapp.app.core.service.user.validation.ValidEmail;
import com.wypychmat.rentals.rentapp.app.core.service.user.validation.ValidPassword;
import com.wypychmat.rentals.rentapp.app.core.service.user.validation.ValidUsername;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@PasswordConfirmation
public class RegistrationRequest {

    // TODO: 15.03.2021 add custom messages

    @ValidUsername
    private String username;

    @Size(max = 60,message = "{com.wypychmat.rentals.rentapp.app.core.controller.dto.request.RegistrationRequest.password.Max}")
    @ValidPassword
    private String password;

    private String rePassword;

    @Size(min = 4, max = 70)
    @ValidEmail
    private String email;

    @Size(min = 2, max = 45)
    @NotBlank
    private String firstName;

    @Size(min = 2, max = 60)
    @NotBlank
    private String lastName;

    public RegistrationRequest(String username, String password, String rePassword, String email, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.rePassword = rePassword;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public RegistrationRequest() {
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

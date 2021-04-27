package com.wypychmat.rentals.rentapp.app.core.dto.registration;


import com.wypychmat.rentals.rentapp.app.core.validation.user.RePasswordConstraint;
import com.wypychmat.rentals.rentapp.app.core.validation.user.EmailConstraint;
import com.wypychmat.rentals.rentapp.app.core.validation.user.PasswordConstraint;
import com.wypychmat.rentals.rentapp.app.core.validation.user.UsernameConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RePasswordConstraint
public class RegistrationRequest {

    // TODO: 15.03.2021 add custom messages

    @UsernameConstraint
    private String username;

    @Size(max = 60,message = "{com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationRequest.password.Max}")
    @PasswordConstraint
    private String password;

    private String rePassword;

    @Size(min = 4, max = 70)
    @EmailConstraint
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

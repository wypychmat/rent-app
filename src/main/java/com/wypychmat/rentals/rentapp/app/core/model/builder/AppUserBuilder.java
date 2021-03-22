package com.wypychmat.rentals.rentapp.app.core.model.builder;

import com.wypychmat.rentals.rentapp.app.core.model.user.User;

import java.util.Objects;
import java.util.stream.Stream;

public class AppUserBuilder {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    private AppUserBuilder() {
    }

    public AppUserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public AppUserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public AppUserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public AppUserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AppUserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public static AppUserBuilder builder(){
        return new AppUserBuilder();
    }

    public User build() {
        boolean isNotNull = checkNull();
        if(isNotNull) {
            return new User(
                    this.username,
                    this.password,
                    this.email,
                    this.firstName,
                    this.lastName,
                    false);
        }
        throw new IllegalStateException("All fields should be set");
    }

    private boolean checkNull() {
        return !Stream.of(username, password,email,firstName,lastName)
                .allMatch(Objects::isNull);
    }
}

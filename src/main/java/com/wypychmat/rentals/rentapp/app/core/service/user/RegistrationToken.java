package com.wypychmat.rentals.rentapp.app.core.service.user;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class RegistrationToken {
    private String username;
    private String email;
    private String registrationToken;

    private RegistrationToken(String username, String email, String registrationToken) {
        this.username = username;
        this.email = email;
        this.registrationToken = registrationToken;
    }

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

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String username;
        private String email;
        private String registrationToken;

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setRegistrationToken(String registrationToken) {
            this.registrationToken = registrationToken;
            return this;
        }

        public Optional<RegistrationToken> build() {
            if (!Stream.of(username, email, registrationToken)
                    .allMatch(Objects::isNull)) {
                return Optional.of(new RegistrationToken(username, email, registrationToken));
            }
            return Optional.empty();
        }
    }
}

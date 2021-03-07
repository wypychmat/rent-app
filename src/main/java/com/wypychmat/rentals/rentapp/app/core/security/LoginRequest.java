package com.wypychmat.rentals.rentapp.app.core.security;

class LoginRequest {
    private String username;
    private String password;

    LoginRequest() {
    }

    String getUsername() throws IncorrectPayloadException {
        checkNullFields();
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    String getPassword() throws IncorrectPayloadException {
        checkNullFields();
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    private void checkNullFields() throws IncorrectPayloadException {
        if (username == null || password == null) {
            if (username == null && password == null)
                throw new IncorrectPayloadException("Missing payload");
            else
                throw new IncorrectPayloadException((username == null) ? "Missing username" : "Missing password");
        }
    }

}

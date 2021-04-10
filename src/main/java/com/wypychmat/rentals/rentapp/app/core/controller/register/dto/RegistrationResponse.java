package com.wypychmat.rentals.rentapp.app.core.controller.register.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public class RegistrationResponse {
    private int status;
    @JsonProperty("user_id")
    private Long userId;
    private String message;

    public RegistrationResponse(HttpStatus status, Long userId, String message) {
        this.status = status.value();
        this.userId = userId;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

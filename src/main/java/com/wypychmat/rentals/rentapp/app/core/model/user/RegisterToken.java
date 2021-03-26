package com.wypychmat.rentals.rentapp.app.core.model.user;

import javax.persistence.*;

@Entity
public class RegisterToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Long epochCreatedAt;

    private Long epochExpiredAt;

    private boolean isConfirmed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public RegisterToken() {
    }

    public RegisterToken(String token, Long epochCreatedAt, Long epochExpiredAt, User user) {
        this.token = token;
        this.epochCreatedAt = epochCreatedAt;
        this.epochExpiredAt = epochExpiredAt;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEpochCreatedAt() {
        return epochCreatedAt;
    }

    public void setEpochCreatedAt(Long epochCreatedAt) {
        this.epochCreatedAt = epochCreatedAt;
    }

    public Long getEpochExpiredAt() {
        return epochExpiredAt;
    }

    public void setEpochExpiredAt(Long epochExpiredAt) {
        this.epochExpiredAt = epochExpiredAt;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

package com.wypychmat.rentals.rentapp.app.core.model.user;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class RegisterToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String token;

    private Long epochCreatedAt;

    private Long epochExpiredAt;

    private boolean isConfirmed;

    private LocalDateTime confirmedAt;

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

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterToken that = (RegisterToken) o;
        return isConfirmed == that.isConfirmed && Objects.equals(id, that.id) && Objects.equals(token, that.token) && Objects.equals(epochCreatedAt, that.epochCreatedAt) && Objects.equals(epochExpiredAt, that.epochExpiredAt) && Objects.equals(confirmedAt, that.confirmedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, epochCreatedAt, epochExpiredAt, isConfirmed, confirmedAt);
    }
}

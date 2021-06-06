package com.wypychmat.rentals.rentapp.app.core.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
class JwtConfig {

    private long expirationInMin;
    private String prefix;
    private String contentType;
    private String authorities;
    private String username;

    private JwtConfig() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getExpirationInMin() {
        return expirationInMin;
    }

    public void setExpirationInMin(long expirationInMin) {
        this.expirationInMin = expirationInMin;
    }

    public String getPrefix() {
        return prefix + " ";
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
}

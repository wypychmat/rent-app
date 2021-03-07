package com.wypychmat.rentals.rentapp.app.core.security;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "rsa.config")
public class RsaKeyConfig {

    private String privatePath;
    private String publicPath;
    private String privateHeader;
    private String publicHeader;

    private RsaKeyConfig() {
    }

    public String getPrivatePath() {
        return privatePath;
    }

    public String getPublicPath() {
        return publicPath;
    }

    public void setPrivatePath(String privatePath) {
        this.privatePath = privatePath;
    }

    public void setPublicPath(String publicPath) {
        this.publicPath = publicPath;
    }

    public String getPrivateHeader() {
        return privateHeader;
    }

    public void setPrivateHeader(String privateHeader) {
        this.privateHeader = privateHeader;
    }

    public String getPublicHeader() {
        return publicHeader;
    }

    public void setPublicHeader(String publicHeader) {
        this.publicHeader = publicHeader;
    }
}

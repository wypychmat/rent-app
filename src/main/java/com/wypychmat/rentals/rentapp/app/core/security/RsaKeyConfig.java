package com.wypychmat.rentals.rentapp.app.core.security;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "rsa.config")
 class RsaKeyConfig {

    private String privatePath;
    private String publicPath;
    private String privateHeader;
    private String publicHeader;

    private RsaKeyConfig() {
    }

    String getPrivatePath() {
        return privatePath;
    }

    String getPublicPath() {
        return publicPath;
    }

    void setPrivatePath(String privatePath) {
        this.privatePath = privatePath;
    }

    void setPublicPath(String publicPath) {
        this.publicPath = publicPath;
    }

    String getPrivateHeader() {
        return privateHeader;
    }

    void setPrivateHeader(String privateHeader) {
        this.privateHeader = privateHeader;
    }

    String getPublicHeader() {
        return publicHeader;
    }

    void setPublicHeader(String publicHeader) {
        this.publicHeader = publicHeader;
    }
}

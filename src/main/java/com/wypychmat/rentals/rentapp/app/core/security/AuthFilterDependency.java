package com.wypychmat.rentals.rentapp.app.core.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.wypychmat.rentals.rentapp.app.core.internationalization.MessageProviderCenter;

class AuthFilterDependency {
    private final MessageProviderCenter messageProviderCenter;
    private final Algorithm algorithm;
    private final JwtConfig jwtConfig;

    AuthFilterDependency(MessageProviderCenter messageProviderCenter, Algorithm algorithm, JwtConfig jwtConfig) {
        this.messageProviderCenter = messageProviderCenter;
        this.algorithm = algorithm;
        this.jwtConfig = jwtConfig;
    }

    MessageProviderCenter getMessageSource() {
        return messageProviderCenter;
    }

    Algorithm getAlgorithm() {
        return algorithm;
    }

    JwtConfig getJwtConfig() {
        return jwtConfig;
    }
}

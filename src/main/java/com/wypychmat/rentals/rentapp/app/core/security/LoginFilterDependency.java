package com.wypychmat.rentals.rentapp.app.core.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.wypychmat.rentals.rentapp.app.core.internationalization.MessageProviderCenter;
import org.springframework.security.authentication.AuthenticationManager;

import javax.validation.Validator;

class LoginFilterDependency extends AuthFilterDependency {
    private final Validator validator;
    private AuthenticationManager authenticationManager;
    private final String loginPath;

    private LoginFilterDependency(MessageProviderCenter messageProviderCenter,
                                  Algorithm algorithm,
                                  JwtConfig jwtConfig,
                                  Validator validator,
                                  String loginPath) {

        super(messageProviderCenter, algorithm, jwtConfig);
        this.validator = validator;
        this.loginPath = loginPath;
    }

    static LoginFilterDependency from(AuthFilterDependency authFilterDependency,
                                      String loginPath, Validator validator) {
        return new LoginFilterDependency(authFilterDependency.getMessageSource()
                , authFilterDependency.getAlgorithm()
                , authFilterDependency.getJwtConfig(),
                validator,
                loginPath);
    }

    AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    String getLoginPath() {
        return loginPath;
    }

    void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    Validator getValidator() {
        return validator;
    }
}

package com.wypychmat.rentals.rentapp.app.core.security;

import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class LoginRequestVerifier {
    private final Validator validator;

    LoginRequestVerifier(Validator validator) {
        this.validator = validator;
    }

    void validateLoginRequest(LoginRequest loginRequest) {
        Set<ConstraintViolation<LoginRequest>> validate = validator.validate(loginRequest);
        if (!validate.isEmpty()) {
            Map<String, String> errors = validate.stream()
                    .collect(Collectors.toMap(e -> e.getPropertyPath().toString(), ConstraintViolation::getMessage));
            throw new BadRequestAuthException(HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);
        }
    }


}

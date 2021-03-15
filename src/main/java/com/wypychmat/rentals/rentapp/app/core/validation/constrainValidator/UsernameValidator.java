package com.wypychmat.rentals.rentapp.app.core.validation.constrainValidator;

import com.wypychmat.rentals.rentapp.app.core.validation.config.ValidUsername;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UsernameValidator implements ConstraintValidator<ValidUsername,String> {
    private Pattern pattern;
    private static final String REGEX = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,43}[a-zA-Z0-9]$";
    @Override
    public void initialize(ValidUsername constraintAnnotation) {
        pattern = Pattern.compile(REGEX);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return username != null && pattern.matcher(username).matches();
    }
}

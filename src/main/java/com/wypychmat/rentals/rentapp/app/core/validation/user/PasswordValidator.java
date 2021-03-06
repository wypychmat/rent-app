package com.wypychmat.rentals.rentapp.app.core.validation.user;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {
    private Pattern pattern;
    private static final String REGEX = "^(?=.*?[A-Z])(?=(.*[a-z])+)(?=(.*[\\d])+)(?=(.*[\\W])+)(?!.*\\s).{8,60}$";

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        pattern = Pattern.compile(REGEX);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password != null && pattern.matcher(password).matches();
    }
}

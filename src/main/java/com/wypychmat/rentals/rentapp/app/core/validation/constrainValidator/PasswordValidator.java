package com.wypychmat.rentals.rentapp.app.core.validation.constrainValidator;

import com.wypychmat.rentals.rentapp.app.core.validation.config.ValidPassword;
import com.wypychmat.rentals.rentapp.app.core.validation.config.ValidUsername;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidPassword,String> {
    private Pattern pattern;
    private static final String REGEX = "^(?=.*?[A-Z])(?=(.*[a-z])+)(?=(.*[\\d])+)(?=(.*[\\W])+)(?!.*\\s).{8,60}$";
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        pattern = Pattern.compile(REGEX);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password != null && pattern.matcher(password).matches();
    }
}

package com.wypychmat.rentals.rentapp.app.core.validation.pagable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PageValidator implements ConstraintValidator<ValidPage, String> {

    private final static String PATTERN = "^[0-9]*$";

    private Pattern pattern;

    @Override
    public void initialize(ValidPage constraintAnnotation) {
        pattern = Pattern.compile(PATTERN);
    }

    @Override
    public boolean isValid(String page, ConstraintValidatorContext constraintValidatorContext) {
        return pattern.matcher(page).matches();
    }
}

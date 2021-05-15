package com.wypychmat.rentals.rentapp.app.core.validation.pagable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PageSizeValidator implements ConstraintValidator<ValidPageSize, String> {

    private final static String PATTERN = "^[1-9]*$";

    private Pattern pattern;

    @Override
    public void initialize(ValidPageSize constraintAnnotation) {
        pattern = Pattern.compile(PATTERN);
    }

    @Override
    public boolean isValid(String size, ConstraintValidatorContext constraintValidatorContext) {
        return pattern.matcher(size).matches();
    }


}

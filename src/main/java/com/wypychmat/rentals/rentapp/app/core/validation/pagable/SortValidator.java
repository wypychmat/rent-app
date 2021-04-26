package com.wypychmat.rentals.rentapp.app.core.validation.pagable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SortValidator implements ConstraintValidator<ValidSort, String[]> {

    private final static String FIRST_PATTERN = "^[a-zA-Z\\.]+";
    private final static String SECOND_PATTERN = "^[a-zA-Z\\.]*\\.((\\basc\\b)|(\\bdesc\\b))$";
    private final static String POINT_PATTERN = "[^\\.]*\\.";

    private Pattern firstPattern;
    private Pattern secondPattern;
    private Pattern pointPattern;

    @Override
    public void initialize(ValidSort constraintAnnotation) {
        firstPattern = Pattern.compile(FIRST_PATTERN);
        pointPattern = Pattern.compile(POINT_PATTERN);
        secondPattern = Pattern.compile(SECOND_PATTERN);
    }

    @Override
    public boolean isValid(String[] sorts, ConstraintValidatorContext constraintValidatorContext) {
        for (String sort : sorts) {
            if (!firstPattern.matcher(sort).matches())
                return false;
            int occurrence = countPoint(sort);
            if (occurrence == 1) {
                if (!secondPattern.matcher(sort).matches())
                    return false;
            } else if (occurrence == 2)
                return false;
        }
        return true;
    }

    private int countPoint(String sort) {
        Matcher matcher = pointPattern.matcher(sort);
        int counter = 0;
        while (matcher.find() && counter < 2) {
            counter++;
        }
        return counter;
    }

}

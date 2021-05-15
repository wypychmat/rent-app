package com.wypychmat.rentals.rentapp.app.core.validation.pagable;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Constraint(validatedBy = PageSizeValidator.class)
@Target(FIELD)
@Retention(RUNTIME)
public @interface ValidPageSize {
    // TODO: 25.04.2021 add message
    String message() default "invalid element per page";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}

package com.wypychmat.rentals.rentapp.app.core.validation.config;


import com.wypychmat.rentals.rentapp.app.core.validation.constrainValidator.RePasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = RePasswordValidator.class)
@Target({TYPE,ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface PasswordConfirmation {
    String message() default "{com.wypychmat.rentals.rentapp.app.core.validation.config.PasswordConfirmation.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String property() default "rePassword";
}

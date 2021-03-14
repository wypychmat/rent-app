package com.wypychmat.rentals.rentapp.app.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;



@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ METHOD, FIELD, CONSTRUCTOR, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidEmail {
    String message() default "{com.wypychmat.rentals.rentapp.app.core.validation.ValidEmail.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}

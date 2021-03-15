package com.wypychmat.rentals.rentapp.app.core.validation.constrainValidator;

import com.wypychmat.rentals.rentapp.app.core.controller.dto.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.validation.config.PasswordConfirmation;
import com.wypychmat.rentals.rentapp.app.core.validation.config.ValidUsername;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class RePasswordValidator implements ConstraintValidator<PasswordConfirmation, RegistrationRequest> {
    public void initialize(PasswordConfirmation constraintAnnotation) {
    }

    @Override
    public boolean isValid(RegistrationRequest request, ConstraintValidatorContext constraintValidatorContext) {
        return request != null
                && request.getPassword() != null
                && request.getRePassword() != null
                && request.getRePassword().equals(request.getPassword());
    }
}

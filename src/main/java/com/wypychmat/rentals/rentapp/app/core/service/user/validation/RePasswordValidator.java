package com.wypychmat.rentals.rentapp.app.core.service.user.validation;

import com.wypychmat.rentals.rentapp.app.core.controller.dto.request.RegistrationRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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

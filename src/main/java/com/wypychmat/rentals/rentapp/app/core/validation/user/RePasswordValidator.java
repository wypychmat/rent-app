package com.wypychmat.rentals.rentapp.app.core.validation.user;

import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RePasswordValidator implements ConstraintValidator<RePasswordConstraint, RegistrationRequest> {
    public void initialize(RePasswordConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(RegistrationRequest request, ConstraintValidatorContext constraintValidatorContext) {
        return request != null
                && request.getPassword() != null
                && request.getRePassword() != null
                && request.getRePassword().equals(request.getPassword());
    }
}

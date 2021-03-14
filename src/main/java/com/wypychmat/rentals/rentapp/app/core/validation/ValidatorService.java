package com.wypychmat.rentals.rentapp.app.core.validation;

import com.wypychmat.rentals.rentapp.app.core.controller.dto.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidRegisterRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ValidatorService {
    private final Validator validator;


    @Autowired
    public ValidatorService(Validator validator) {
        this.validator = validator;
    }

    public boolean verifyRegistrationRequest(RegistrationRequest request) {
        Set<ConstraintViolation<RegistrationRequest>> validate = validator.validate(request);
        if(validate.isEmpty()) {
            return true;
        }
            throw new InvalidRegisterRequestException("invalid request",validate.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList())
            );
    }
}

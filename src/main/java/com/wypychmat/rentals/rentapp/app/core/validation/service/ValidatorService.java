package com.wypychmat.rentals.rentapp.app.core.validation.service;

import com.wypychmat.rentals.rentapp.app.core.controller.dto.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidRegisterRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        Map<String, String> errors = new HashMap<>();
        validate.forEach(item->{
            String message = item.getMessage();
            Path propertyPath = item.getPropertyPath();
            String s = propertyPath.toString();
            if(s.equals(""))
                 s = item.getConstraintDescriptor().getAttributes().get("property").toString();
            if(errors.containsKey(s)){
                String s1 = errors.get(s);
                message = s1 + ";" +  message;
            }
            errors.put(s, message);
        });
        throw new InvalidRegisterRequestException("invalid request",errors);
    }
}

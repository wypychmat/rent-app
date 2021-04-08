package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidUserRequestException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
class UserValidatorServiceImpl implements UserValidatorService {
    private final Validator validator;


    public UserValidatorServiceImpl(Validator validator) {
        this.validator = validator;
    }

    public boolean verifyRegistrationRequest(RegistrationRequest request) {
        Set<ConstraintViolation<RegistrationRequest>> validate = validator.validate(request);
        if (validate.isEmpty()) {
            return true;
        }
        Map<String, String> errors = new HashMap<>();
        validate.forEach(item -> {
            String message = item.getMessage();
            Path propertyPath = item.getPropertyPath();
            String s = propertyPath.toString();
            if (s.equals(""))
                s = item.getConstraintDescriptor().getAttributes().get("property").toString();
            if (errors.containsKey(s)) {
                String s1 = errors.get(s);
                message = s1 + ";" + message;
            }
            errors.put(s, message);
        });
        throw new InvalidUserRequestException("invalid request", errors);
    }
}

package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.controller.register.dto.RefreshConfirmTokenRequest;
import com.wypychmat.rentals.rentapp.app.core.controller.register.dto.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.exception.register.InvalidUserRequestException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class UserValidatorServiceImpl implements UserValidatorService {
    private final Validator validator;

    public UserValidatorServiceImpl(Validator validator) {
        this.validator = validator;
    }

    public void verifyRegistrationRequestOrThrow(RegistrationRequest request) throws InvalidUserRequestException {

        Set<ConstraintViolation<RegistrationRequest>> validate = validator.validate(request);
        if (!validate.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            validate.forEach(item -> {
                String message = item.getMessage();
                Path propertyPath = item.getPropertyPath();
                String s = propertyPath.toString();
                if (s.equals(""))
                    s = item.getConstraintDescriptor().getAttributes().get("property").toString();
                if (s != null && errors.containsKey(s)) {
                    String s1 = errors.get(s);
                    message = s1 + ";" + message;
                }
                errors.put(s, message);
            });
            throw new InvalidUserRequestException("invalid request", errors);
        }
    }

    @Override
    public void verifyRefreshConfirmationTokenRequestOrThrow(RefreshConfirmTokenRequest refreshConfirmTokenRequest)
            throws InvalidUserRequestException {

        Set<ConstraintViolation<RefreshConfirmTokenRequest>> validate = validator.validate(refreshConfirmTokenRequest);
        if (!validate.isEmpty()) {
            Map<String, String> errors = validate.stream()
                    .collect(Collectors.toMap(e -> e.getPropertyPath().toString(), ConstraintViolation::getMessage));
            throw new InvalidUserRequestException("invalid request", errors);
        }
    }
}

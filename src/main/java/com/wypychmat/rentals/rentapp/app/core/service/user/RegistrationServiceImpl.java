package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.controller.dto.request.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.internationalization.LocalMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationServiceImpl extends RegistrationService {

    @Autowired
    public RegistrationServiceImpl(UserValidatorService userValidatorService,
                                   RegisterUserDao registerUserDao,
                                   LocalMessage localMessage) {
        super(userValidatorService, registerUserDao, localMessage);
    }

    @Override
    public Optional<Long> registerUser(RegistrationRequest registrationRequest) {
        if (isRequestValid(registrationRequest)) {
            return attemptRegistration(registrationRequest);
        }
        return Optional.empty();
    }

}

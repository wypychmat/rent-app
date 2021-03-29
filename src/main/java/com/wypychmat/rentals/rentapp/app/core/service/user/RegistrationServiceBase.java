package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.controller.dto.request.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.controller.dto.response.UserDto;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidConfirmationTokenException;
import com.wypychmat.rentals.rentapp.app.core.service.mail.EmailService;
import com.wypychmat.rentals.rentapp.app.core.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

// TODO: 27.03.2021 add messages form properties


abstract class RegistrationServiceBase extends RegistrationService {

    public RegistrationServiceBase(UserValidatorService userValidatorService,
                                   RegisterUserDao registerUserDao,
                                   EmailService emailService,
                                   MessageSource messageSource) {
        super(userValidatorService, registerUserDao, emailService, messageSource);
    }

    @Override
    public Optional<UserDto> registerUser(RegistrationRequest registrationRequest) {
        if (isRequestValid(registrationRequest)) {
            return attemptRegistration(registrationRequest);
        }
        return Optional.empty();
    }

    @Override
    public void confirmToken(String token) {
        if (token != null && !token.trim().equals("")) {
            attemptTokenConfirmation(token);
        } else {
            throw new InvalidConfirmationTokenException("Token must not be empty", HttpStatus.BAD_REQUEST);
        }
    }
}

package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.controller.register.dto.RefreshConfirmTokenRequest;
import com.wypychmat.rentals.rentapp.app.core.controller.register.dto.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.controller.register.dto.RegistrationUserDto;
import com.wypychmat.rentals.rentapp.app.core.exception.register.InvalidConfirmationTokenException;
import com.wypychmat.rentals.rentapp.app.core.mapper.RegistrationMapper;
import com.wypychmat.rentals.rentapp.app.core.service.mail.EmailService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import java.util.Optional;

// TODO: 27.03.2021 add messages form properties


abstract class RegistrationServiceBase extends RegistrationService {

    public RegistrationServiceBase(UserValidatorService userValidatorService,
                                   RegisterUserDao registerUserDao,
                                   EmailService emailService,
                                   MessageSource messageSource,
                                   RegistrationMapper registrationMapper) {
        super(userValidatorService, registerUserDao, emailService, messageSource,registrationMapper);
    }

    @Override
    public Optional<RegistrationUserDto> registerUser(RegistrationRequest registrationRequest) {
        return attemptRegistration(registrationRequest);
    }

    @Override
    public RegistrationUserDto confirmToken(String token) {
        if (token != null && !token.trim().equals("")) {
            return attemptTokenConfirmation(token);
        } else {
            throw new InvalidConfirmationTokenException("Token must not be empty", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Optional<RegistrationUserDto> refreshTokenForUser(RefreshConfirmTokenRequest refreshConfirmTokenRequest) {
        return attemptRefreshTokenForUser(refreshConfirmTokenRequest);
    }
}

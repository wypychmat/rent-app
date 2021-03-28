package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.controller.dto.request.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.controller.dto.response.UserDto;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidConfirmationTokenException;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Optional;

// TODO: 27.03.2021 add messages form properties

@Service
public class RegistrationServiceImpl extends RegistrationService<MimeMessage> {

    @Autowired
    public RegistrationServiceImpl(UserValidatorService userValidatorService,
                                   RegisterUserDao registerUserDao,
                                   EmailService<MimeMessage> emailService,
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

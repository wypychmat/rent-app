package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.controller.dto.request.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.internationalization.LocalMessage;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
public class RegistrationServiceImpl extends RegistrationService<MimeMessage> {

    @Autowired
    public RegistrationServiceImpl(UserValidatorService userValidatorService,
                                   RegisterUserDao registerUserDao,
                                   LocalMessage localMessage,
                                   EmailService<MimeMessage> emailService) {
        super(userValidatorService, registerUserDao, localMessage, emailService);
    }

    @Override
    public Optional<User> registerUser(RegistrationRequest registrationRequest) {
        if (isRequestValid(registrationRequest)) {
            return attemptRegistration(registrationRequest);
        }
        return Optional.empty();
    }

}

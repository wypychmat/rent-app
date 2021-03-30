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

@Service
@Profile("dev")
class RegistrationServiceDev extends RegistrationServiceBase {

    @Autowired
    public RegistrationServiceDev(UserValidatorService userValidatorService,
                                  RegisterUserDao registerUserDao,
                                  @MailService EmailService emailService,
                                  MessageSource messageSource) {
        super(userValidatorService, registerUserDao, emailService, messageSource);
    }
}

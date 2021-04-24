package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.mapper.RegistrationMapper;
import com.wypychmat.rentals.rentapp.app.core.service.mail.EmailService;
import com.wypychmat.rentals.rentapp.app.core.service.mail.MailService;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static com.wypychmat.rentals.rentapp.app.core.service.mail.MailService.*;


@Service
@Profile("test")
class RegistrationServiceTestImplementation extends RegistrationServiceBase {


    public RegistrationServiceTestImplementation(UserValidatorService userValidatorService,
                                                 RegisterUserDao registerUserDao,
                                                 @MailService(type = Type.SIMPLE)EmailService emailService,
                                                 MessageSource messageSource, RegistrationMapper registrationMapper) {
        super(userValidatorService, registerUserDao, emailService, messageSource,registrationMapper);
    }



}

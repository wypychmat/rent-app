package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.mapper.RegistrationMapper;
import com.wypychmat.rentals.rentapp.app.core.service.mail.EmailService;
import com.wypychmat.rentals.rentapp.app.core.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Locale;

// TODO: 27.03.2021 add messages form properties

@Service
@Profile({"dev","drop"})
class RegistrationServiceDev extends RegistrationServiceBase {

    @Autowired
    public RegistrationServiceDev(UserValidatorService userValidatorService,
                                  RegisterUserDao registerUserDao,
                                  @MailService EmailService emailService,
                                  MessageSource messageSource, RegistrationMapper registrationMapper) {
        super(userValidatorService, registerUserDao, emailService, messageSource,registrationMapper);
    }
}

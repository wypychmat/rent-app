package com.wypychmat.rentals.rentapp.app.core.service.mail;

import com.wypychmat.rentals.rentapp.app.core.service.user.RegistrationMessagePayload;

public interface EmailService {

    void sendEmail(RegistrationMessagePayload registrationMessagePayload);
}

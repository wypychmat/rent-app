package com.wypychmat.rentals.rentapp.app.core.service.mail;

import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationMessagePayload;

public interface EmailService {

    void sendEmail(RegistrationMessagePayload registrationMessagePayload);
}

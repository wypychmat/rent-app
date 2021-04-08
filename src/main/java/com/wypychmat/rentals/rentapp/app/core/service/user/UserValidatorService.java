package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationRequest;

interface UserValidatorService {

    boolean verifyRegistrationRequest(RegistrationRequest request);
}

package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.controller.dto.request.RegistrationRequest;

interface UserValidatorService {

    boolean verifyRegistrationRequest(RegistrationRequest request);
}

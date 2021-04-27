package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.dto.registration.RefreshConfirmTokenRequest;
import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.exception.register.InvalidUserRequestException;

interface UserValidatorService {

    void verifyRegistrationRequestOrThrow(RegistrationRequest request) throws InvalidUserRequestException;

    void verifyRefreshConfirmationTokenRequestOrThrow(RefreshConfirmTokenRequest refreshConfirmTokenRequest)
            throws InvalidUserRequestException;
}

package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.controller.register.dto.RefreshConfirmTokenRequest;
import com.wypychmat.rentals.rentapp.app.core.controller.register.dto.RegistrationRequest;

interface UserValidatorService {

    boolean verifyRegistrationRequest(RegistrationRequest request);

    void verifyRefreshConfirmationTokenRequest(RefreshConfirmTokenRequest refreshConfirmTokenRequest);
}

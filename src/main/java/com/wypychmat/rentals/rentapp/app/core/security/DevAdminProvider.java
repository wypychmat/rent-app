package com.wypychmat.rentals.rentapp.app.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
class DevAdminProvider extends AdminProvider {

    @Autowired
    public DevAdminProvider(CreateAdminService createAdminService, PasswordEncoder passwordEncoder) {
        super(createAdminService, passwordEncoder);
    }

    @Override
    protected Admin prepareAdminCredentials() {
        return new Admin("devAdmin", "devPassword");
    }
}

package com.wypychmat.rentals.rentapp.app.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
class TestAdminAndModeratorProvider extends AdminAndModeratorProvider {

    @Autowired
    public TestAdminAndModeratorProvider(CreateAdminAndModeratorService createAdminAndModeratorService, PasswordEncoder passwordEncoder) {
        super(createAdminAndModeratorService, passwordEncoder);
    }

    @Override
    protected AdminOrModerator prepareAdminCredentials() {
        return new AdminOrModerator("testAdmin", "testPassword");
    }

    @Override
    protected AdminOrModerator prepareModeratorCredentials() {
        return new AdminOrModerator("testModerator", "testPassword");
    }
}
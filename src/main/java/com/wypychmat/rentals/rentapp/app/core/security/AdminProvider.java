package com.wypychmat.rentals.rentapp.app.core.security;

import com.wypychmat.rentals.rentapp.app.core.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

abstract class AdminProvider {
    private static final String EMAIL_SUFFIX = "@admin.com";
    public static final String ADMIN = "admin";
    private final CreateAdminService createAdminService;
    private final PasswordEncoder passwordEncoder;

    protected AdminProvider(CreateAdminService createAdminService, PasswordEncoder passwordEncoder) {
        this.createAdminService = createAdminService;
        this.passwordEncoder = passwordEncoder;
    }

    protected abstract Admin prepareAdminCredentials();


    @PostConstruct
    void createAdministrator() {
        Admin admin = prepareAdminCredentials();
        String adminName = admin.getAdminName();
        User user = new User();
        user.setUsername(adminName);
        user.setPassword(passwordEncoder.encode(admin.getPassword()));
        user.setEmail(adminName + EMAIL_SUFFIX);
        user.setFirstName(ADMIN);
        user.setLastName(adminName);
        user.setEnabled(true);
        createAdminService.saveAdminIfNotExist(user);
    }
}

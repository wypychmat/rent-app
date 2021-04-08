package com.wypychmat.rentals.rentapp.app.core.security;

import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import com.wypychmat.rentals.rentapp.app.core.model.user.ApplicationMainRole;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

abstract class AdminAndModeratorProvider {
    private static final String EMAIL_SUFFIX = "@admin.com";
    public static final String ADMIN = "admin";
    private final CreateAdminAndModeratorService createAdminAndModeratorService;
    private final PasswordEncoder passwordEncoder;

    protected AdminAndModeratorProvider(CreateAdminAndModeratorService createAdminAndModeratorService, PasswordEncoder passwordEncoder) {
        this.createAdminAndModeratorService = createAdminAndModeratorService;
        this.passwordEncoder = passwordEncoder;
    }

    protected abstract AdminOrModerator prepareAdminCredentials();
    protected abstract AdminOrModerator prepareModeratorCredentials();


    @PostConstruct
    void createManagementUser() {
        createSuperUserIfNotExist(prepareAdminCredentials(),ApplicationMainRole.ADMIN);
        createSuperUserIfNotExist(prepareModeratorCredentials(),ApplicationMainRole.MODERATOR);
    }

    private void createSuperUserIfNotExist(AdminOrModerator adminOrModerator, ApplicationMainRole appRole) {
        String name = adminOrModerator.getName();
        User user = new User();
        user.setUsername(name);
        user.setPassword(passwordEncoder.encode(adminOrModerator.getPassword()));
        user.setEmail(name + EMAIL_SUFFIX);
        user.setFirstName(name);
        user.setLastName(ADMIN);
        user.setEnabled(true);
        createAdminAndModeratorService.saveAdminOrModIfNotExist(user,appRole);
    }
}

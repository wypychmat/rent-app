package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.Constant;
import com.wypychmat.rentals.rentapp.app.core.controller.dto.request.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidUserRequestException;
import com.wypychmat.rentals.rentapp.app.core.internationalization.registration.RegistrationMessageProvider;
import com.wypychmat.rentals.rentapp.app.core.model.builder.AppUserBuilder;
import com.wypychmat.rentals.rentapp.app.core.model.projection.UsernameEmail;
import com.wypychmat.rentals.rentapp.app.core.model.user.RegisterToken;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import org.springframework.context.MessageSource;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

abstract class RegistrationService<T> {
    protected final UserValidatorService userValidatorService;
    protected final RegisterUserDao registerUserDao;
    protected final EmailService<T> emailService;
    protected final RegistrationMessageProvider messageProvider;

    protected RegistrationService(UserValidatorService userValidatorService,
                                  RegisterUserDao registerUserDao, EmailService<T> emailService,
                                  MessageSource messageSource) {
        this.userValidatorService = userValidatorService;
        this.registerUserDao = registerUserDao;
        this.emailService = emailService;
        this.messageProvider = new RegistrationMessageProvider(messageSource);
    }

    public abstract Optional<User> registerUser(RegistrationRequest registrationRequest);

    public abstract void confirmToken(String token);

    protected boolean isRequestValid(RegistrationRequest registrationRequest) {
        return userValidatorService.verifyRegistrationRequest(registrationRequest);
    }

    protected Optional<User> attemptRegistration(RegistrationRequest registrationRequest) {
        if (checkUserNotExist(registrationRequest)) {
            Optional<User> user = registerUserDao.saveUser(createUserFromRequest(registrationRequest));
            if (user.isPresent()) {
                return attemptToSaveRegistrationToken(user.get());
            }
        }
        return Optional.empty();
    }

    private Optional<User> attemptToSaveRegistrationToken(User user) {
        Optional<RegisterToken> registrationToken = getRegistrationToken(user);
        if (registrationToken.isPresent()) {
            RegisterToken newToken = registrationToken.get();
            Optional<RegisterToken> savedToken = registerUserDao.saveToken(newToken);
            if (savedToken.isPresent()) {
                emailService.sendEmail(mapRegistrationToken(newToken));
                user.setPassword("");
                return Optional.of(user);
            } else {
                registerUserDao.deleteUserByUsername(user.getUsername());
            }
        } else {
            registerUserDao.deleteUserByUsername(user.getUsername());
        }
        return Optional.empty();
    }

    protected void attemptTokenConfirmation(String token) {

    }

    private RegistrationMessagePayload mapRegistrationToken(RegisterToken token) {
        User user = token.getUser();
        return new RegistrationMessagePayload(user.getUsername(), user.getEmail(), token.getToken());
    }

    private Optional<RegisterToken> getRegistrationToken(User user) {
        AtomicInteger maxTries = new AtomicInteger(5);
        do {
            String newToken = ConfirmationTokenBuilder.build(user.getUsername(), user.getEmail());
            Optional<RegisterToken> tokenInDb = registerUserDao.findToken(newToken);
            if (tokenInDb.isPresent()) {
                long now = System.currentTimeMillis();
                long expired = now + Constant.HOUR_24;
                return Optional.of(new RegisterToken(newToken, now, expired, user));
            } else {
                maxTries.decrementAndGet();
            }
        } while (maxTries.get() >= 0);
        return Optional.empty();
    }

    protected boolean checkUserNotExist(RegistrationRequest registrationRequest) {
        Optional<UsernameEmail> existUser = registerUserDao.existByUsernameAndEmail(
                registrationRequest.getUsername(),
                registrationRequest.getEmail());
        if (existUser.isEmpty()) {
            return true;
        }
        throw new InvalidUserRequestException("User exist",
                messageProvider.getRegistrationErrors(registrationRequest, existUser.get()));
    }

    protected User createUserFromRequest(RegistrationRequest registrationRequest) {
        return AppUserBuilder.builder()
                .setUsername(registrationRequest.getUsername())
                .setEmail(registrationRequest.getEmail())
                .setPassword(registrationRequest.getPassword())
                .setFirstName(registrationRequest.getFirstName())
                .setLastName(registrationRequest.getLastName())
                .build();
    }
}

package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.Constant;
import com.wypychmat.rentals.rentapp.app.core.controller.dto.request.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidConfirmationTokenException;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidUserRequestException;
import com.wypychmat.rentals.rentapp.app.core.internationalization.registration.RegistrationMessageProvider;
import com.wypychmat.rentals.rentapp.app.core.model.builder.AppUserBuilder;
import com.wypychmat.rentals.rentapp.app.core.model.projection.UsernameEmail;
import com.wypychmat.rentals.rentapp.app.core.model.user.RegisterToken;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


// TODO: 28.03.2021 add message from properties
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
                System.out.println("user SAved");
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
        Optional<RegisterToken> registerToken = registerUserDao.findToken(token);
        if (registerToken.isPresent()) {
            proceedOnConfirmationToken(registerToken.get());
        } else {
            throw new InvalidConfirmationTokenException("Invalid Registration Token", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    private void proceedOnConfirmationToken(RegisterToken registerToken) {
        checkIsTokenAlreadyConfirmed(registerToken);
        checkIsTokenNotExpired(registerToken);
        enableUser(registerToken);
    }

    private void enableUser(RegisterToken registerToken) {
        registerToken.setConfirmedAt(LocalDateTime.now());
        registerToken.setConfirmed(true);
        Long userId = registerToken.getUser().getId();
        registerUserDao.saveToken(registerToken);
        registerUserDao.enableUserById(userId);
    }

    private void checkIsTokenNotExpired(RegisterToken registerToken) {
        long actualTime = System.currentTimeMillis();
        if (actualTime > registerToken.getEpochExpiredAt()) {
            throw new InvalidConfirmationTokenException("Token expired", HttpStatus.NOT_FOUND);
        }
    }

    private void checkIsTokenAlreadyConfirmed(RegisterToken registerToken) {
        if (registerToken.isConfirmed()) {
            throw new InvalidConfirmationTokenException("Already Confirmed", HttpStatus.NOT_FOUND);
        }
    }

    private RegistrationMessagePayload mapRegistrationToken(RegisterToken token) {
        User user = token.getUser();
        return new RegistrationMessagePayload(user.getUsername(), user.getEmail(), token.getToken());
    }

    private Optional<RegisterToken> getRegistrationToken(User user) {
        AtomicInteger maxTries = new AtomicInteger(3);
        do {
            String newToken = ConfirmationTokenBuilder.build(user.getUsername(), user.getEmail());
            Optional<RegisterToken> tokenInDb = registerUserDao.findToken(newToken);
            if (tokenInDb.isEmpty()) {
                long now = System.currentTimeMillis();
                long expired = now + Constant.HOUR_24;
                return Optional.of(new RegisterToken(newToken, now, expired, user));
            }
        } while (maxTries.decrementAndGet() >= 0);
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

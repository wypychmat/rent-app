package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.dto.registration.RefreshConfirmTokenRequest;
import com.wypychmat.rentals.rentapp.app.core.util.Constant;
import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.dto.UserDto;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidConfirmationTokenException;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidUserRequestException;
import com.wypychmat.rentals.rentapp.app.core.internationalization.registration.RegistrationMessageProvider;
import com.wypychmat.rentals.rentapp.app.core.model.builder.AppUserBuilder;
import com.wypychmat.rentals.rentapp.app.core.model.projection.UsernameEmail;
import com.wypychmat.rentals.rentapp.app.core.model.user.RegisterToken;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import com.wypychmat.rentals.rentapp.app.core.service.mail.EmailService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


// TODO: 28.03.2021 add message from properties
public abstract class RegistrationService {
    protected final UserValidatorService userValidatorService;
    protected final RegisterUserDao registerUserDao;
    protected final EmailService emailService;
    protected final RegistrationMessageProvider messageProvider;

    protected RegistrationService(UserValidatorService userValidatorService,
                                  RegisterUserDao registerUserDao, EmailService emailService,
                                  MessageSource messageSource) {
        this.userValidatorService = userValidatorService;
        this.registerUserDao = registerUserDao;
        this.emailService = emailService;
        this.messageProvider = new RegistrationMessageProvider(messageSource);
    }

    public abstract Optional<UserDto> registerUser(RegistrationRequest registrationRequest);

    public abstract UserDto confirmToken(String token);

    public abstract void refreshTokenForUser(RefreshConfirmTokenRequest refreshConfirmTokenRequest);

    protected boolean isRequestValid(RegistrationRequest registrationRequest) {
        return userValidatorService.verifyRegistrationRequest(registrationRequest);
    }

    protected void attemptRefreshTokenForUser(RefreshConfirmTokenRequest refreshConfirmTokenRequest) {
        userValidatorService.verifyRefreshConfirmationTokenRequest(refreshConfirmTokenRequest);
    }

    protected Optional<UserDto> attemptRegistration(RegistrationRequest registrationRequest) {
        if (checkUserNotExist(registrationRequest)) {
            Optional<User> user = registerUserDao.saveUser(createUserFromRequest(registrationRequest));
            if (user.isPresent()) {
                return attemptToSaveRegistrationToken(user.get());
            }
        }
        return Optional.empty();
    }


    private Optional<UserDto> attemptToSaveRegistrationToken(User user) {
        Optional<RegisterToken> registrationToken = getRegistrationToken(user);
        if (registrationToken.isPresent()) {
            RegisterToken newToken = registrationToken.get();
            Optional<RegisterToken> savedToken = registerUserDao.saveToken(newToken);
            if (savedToken.isPresent()) {
                emailService.sendEmail(mapRegistrationToken(newToken));
                return Optional.of(new UserDto(user.getId(), user.getUsername(), user.getEmail()));
            } else {
                registerUserDao.deleteUserByUsername(user.getUsername());
            }
        } else {
            registerUserDao.deleteUserByUsername(user.getUsername());
        }
        return Optional.empty();
    }

    protected UserDto attemptTokenConfirmation(String token) {
        Optional<RegisterToken> registerToken = registerUserDao.findToken(token);
        if (registerToken.isPresent()) {
            return proceedOnConfirmationToken(registerToken.get());
        } else {
            throw new InvalidConfirmationTokenException("Invalid Registration Token", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    private UserDto proceedOnConfirmationToken(RegisterToken registerToken) {
        checkIsTokenAlreadyConfirmed(registerToken);
        checkIsTokenNotExpired(registerToken);
        return enableUser(registerToken);
    }

    private UserDto enableUser(RegisterToken registerToken) {
        registerToken.setConfirmedAt(LocalDateTime.now());
        registerToken.setConfirmed(true);
        User user = registerToken.getUser();
        Long userId = user.getId();
        registerUserDao.saveToken(registerToken);
        registerUserDao.enableUserById(userId);
        deleteAllOtherConfirmationTokenForUser(registerToken, userId);
        return new UserDto(userId, user.getUsername(), user.getEmail());
    }

    private void deleteAllOtherConfirmationTokenForUser(RegisterToken registerToken, long userId) {
        registerUserDao.deleteTokenExpectGiven(registerToken, userId);
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
        if (existUser.isPresent()) {
            throw new InvalidUserRequestException("User exist",
                    messageProvider.getRegistrationErrors(registrationRequest, existUser.get()));
        }
        return true;

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

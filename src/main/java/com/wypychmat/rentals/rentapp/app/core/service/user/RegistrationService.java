package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.dto.registration.RefreshConfirmTokenRequest;
import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationMessagePayload;
import com.wypychmat.rentals.rentapp.app.core.mapper.RegistrationMapper;
import com.wypychmat.rentals.rentapp.app.core.util.Constant;
import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationUserDto;
import com.wypychmat.rentals.rentapp.app.core.exception.register.InvalidConfirmationTokenException;
import com.wypychmat.rentals.rentapp.app.core.exception.register.InvalidUserRequestException;
import com.wypychmat.rentals.rentapp.app.core.internationalization.registration.RegistrationMessageProvider;
import com.wypychmat.rentals.rentapp.app.core.model.projection.user.UsernameEmail;
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
    private final RegistrationMapper registrationMapper;

    protected RegistrationService(UserValidatorService userValidatorService,
                                  RegisterUserDao registerUserDao, EmailService emailService,
                                  MessageSource messageSource,
                                  RegistrationMapper registrationMapper) {
        this.userValidatorService = userValidatorService;
        this.registerUserDao = registerUserDao;
        this.emailService = emailService;
        this.messageProvider = new RegistrationMessageProvider(messageSource);
        this.registrationMapper = registrationMapper;
    }

    public abstract Optional<RegistrationUserDto> registerUser(RegistrationRequest registrationRequest);

    public abstract RegistrationUserDto confirmToken(String token);

    public abstract Optional<RegistrationUserDto> refreshTokenForUser(RefreshConfirmTokenRequest refreshConfirmTokenRequest);


    protected Optional<RegistrationUserDto> attemptRefreshTokenForUser(RefreshConfirmTokenRequest refreshConfirmTokenRequest) {

        userValidatorService.verifyRefreshConfirmationTokenRequestOrThrow(refreshConfirmTokenRequest);
        Optional<User> optionalUser = registerUserDao.getUserWhenUserExistByUsernameAndEmail(refreshConfirmTokenRequest);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!user.isEnabled()) {
                return attemptRefreshRegistrationToken(user);
            } else {
                throw new InvalidConfirmationTokenException("Already Confirmed", HttpStatus.NOT_FOUND);
            }
        }
        throw new InvalidConfirmationTokenException("User Not EXIST", HttpStatus.NOT_FOUND);
    }

    protected Optional<RegistrationUserDto> attemptRegistration(RegistrationRequest registrationRequest) {
        userValidatorService.verifyRegistrationRequestOrThrow(registrationRequest);
        checkUserNotExistOrThrow(registrationRequest);
        User userFromRequest = registrationMapper.registrationRequestToUser(registrationRequest);
        Optional<RegisterToken> registrationToken = getRegistrationToken(userFromRequest);
        if (registrationToken.isPresent()) {
            try {
                User user = registerUser(userFromRequest, registrationToken.get());
                return Optional.of(registrationMapper.userToRegistration(user));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    private User registerUser(User userFromRequest, RegisterToken registrationToken) {
        User user = registerUserDao.registerUser(userFromRequest, registrationToken);
        emailService.sendEmail(mapRegistrationToken(registrationToken));
        return user;
    }

    private Optional<RegistrationUserDto> attemptRefreshRegistrationToken(User user) {
        Optional<RegisterToken> registrationToken = getRegistrationToken(user);
        if (registrationToken.isPresent()) {
            RegisterToken newToken = registrationToken.get();
            Optional<RegisterToken> savedToken = registerUserDao.saveToken(newToken);
            if (savedToken.isPresent()) {
                emailService.sendEmail(mapRegistrationToken(newToken));
                return Optional.of(registrationMapper.userToRegistration(user));
            }
        }
        return Optional.empty();
    }

    protected RegistrationUserDto attemptTokenConfirmation(String token) {
        Optional<RegisterToken> registerToken = registerUserDao.findToken(token);
        if (registerToken.isPresent()) {
            return proceedOnConfirmationToken(registerToken.get());
        } else {
            throw new InvalidConfirmationTokenException("Invalid Registration Token", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    private RegistrationUserDto proceedOnConfirmationToken(RegisterToken registerToken) {
        checkIsTokenAlreadyConfirmed(registerToken);
        checkIsTokenNotExpired(registerToken);
        return enableUser(registerToken);
    }

    private RegistrationUserDto enableUser(RegisterToken registerToken) {
        registerToken.setConfirmedAt(LocalDateTime.now());
        registerToken.setConfirmed(true);
        User user = registerToken.getUser();
        Long userId = user.getId();
        registerUserDao.saveToken(registerToken);
        registerUserDao.enableUserById(userId);
        deleteAllOtherConfirmationTokenForUser(registerToken, userId);
        return registrationMapper.userToRegistration(user);
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

    protected void checkUserNotExistOrThrow(RegistrationRequest registrationRequest) {
        Optional<UsernameEmail> existUser = registerUserDao.existByUsernameAndEmail(
                registrationRequest.getUsername(),
                registrationRequest.getEmail());
        if (existUser.isPresent()) {
            throw new InvalidUserRequestException("User exist",
                    messageProvider.getRegistrationErrors(registrationRequest, existUser.get()));
        }
    }
}

package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.Constant;
import com.wypychmat.rentals.rentapp.app.core.controller.dto.request.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidUserRequestException;
import com.wypychmat.rentals.rentapp.app.core.internationalization.LocalMessage;
import com.wypychmat.rentals.rentapp.app.core.model.builder.AppUserBuilder;
import com.wypychmat.rentals.rentapp.app.core.model.projection.UsernameEmail;
import com.wypychmat.rentals.rentapp.app.core.model.user.RegisterToken;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

abstract class RegistrationService<T> {
    protected final UserValidatorService userValidatorService;
    protected final RegisterUserDao registerUserDao;
    protected final LocalMessage localMessage;
    protected final EmailService<T> emailService;

    protected RegistrationService(UserValidatorService userValidatorService,
                                  RegisterUserDao registerUserDao, LocalMessage localMessage, EmailService<T> emailService) {
        this.userValidatorService = userValidatorService;
        this.registerUserDao = registerUserDao;
        this.localMessage = localMessage;
        this.emailService = emailService;
    }

    public abstract Optional<User> registerUser(RegistrationRequest registrationRequest);

    protected boolean isRequestValid(RegistrationRequest registrationRequest) {
        return userValidatorService.verifyRegistrationRequest(registrationRequest);
    }

    protected Optional<User> attemptRegistration(RegistrationRequest registrationRequest) {
        if (checkUserNotExist(registrationRequest)) {
            Optional<User> optionalUser = registerUserDao.saveUser(createUserFromRequest(registrationRequest));
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                RegisterToken newToken = getRegistrationToken(user);
                Optional<RegisterToken> savedToken = registerUserDao.saveToken(newToken);
                if (savedToken.isPresent()) {
                    emailService.sendEmail(mapRegistrationToken(newToken));
                    user.setPassword("");
                    return optionalUser;
                }
            }
        }
        return Optional.empty();
    }

    private RegistrationMessagePayload mapRegistrationToken(RegisterToken token) {
        User user = token.getUser();
        return new RegistrationMessagePayload(user.getUsername(), user.getEmail(), token.getToken());
    }

    private RegisterToken getRegistrationToken(User user) {
        String token = ConfirmationTokenBuilder.build(user.getUsername(), user.getEmail());
        long now = System.currentTimeMillis();
        long expired = now + Constant.HOUR_24;
        return new RegisterToken(token, now, expired, user);
    }

    protected boolean checkUserNotExist(RegistrationRequest registrationRequest) {
        Optional<UsernameEmail> existUser = registerUserDao.existByUsernameAndEmail(
                registrationRequest.getUsername(),
                registrationRequest.getEmail());
        if (existUser.isEmpty()) {
            return true;
        }
        throw new InvalidUserRequestException("User exist", getRegistrationErrors(registrationRequest, existUser.get()));
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

    private Map<String, String> getRegistrationErrors(RegistrationRequest registrationRequest, UsernameEmail user) {
        String withGiven = localMessage.getLocalizedMessage("error.user.with.given");
        String alreadyExist = localMessage.getLocalizedMessage("error.already.exist");
        HashMap<String, String> errors = new HashMap<>();
        if (user.getUsername().equals(registrationRequest.getUsername())) {
            String format = getRegistrationErrorMessage(withGiven, alreadyExist, "error.username.exist");
            errors.put("username", format);
        }
        if (user.getEmail().equals(registrationRequest.getEmail())) {
            String format = getRegistrationErrorMessage(withGiven, alreadyExist, "error.email.exist");
            errors.put("email", format);
        }
        return errors;
    }

    private String getRegistrationErrorMessage(String withGiven, String alreadyExist, String messageKey) {
        String baseMessage = localMessage.getLocalizedMessage(messageKey);
        MessageFormat formatter = new MessageFormat(baseMessage);
        return formatter.format(new Object[]{withGiven, alreadyExist});
    }
}

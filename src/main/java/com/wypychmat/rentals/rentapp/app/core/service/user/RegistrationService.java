package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.controller.dto.request.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidUserRequestException;
import com.wypychmat.rentals.rentapp.app.core.internationalization.LocalMessage;
import com.wypychmat.rentals.rentapp.app.core.model.builder.AppUserBuilder;
import com.wypychmat.rentals.rentapp.app.core.model.projection.UsernameEmail;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

abstract class RegistrationService {
    protected final UserValidatorService userValidatorService;
    protected final RegisterUserDao registerUserDao;
    protected final LocalMessage localMessage;

    protected RegistrationService(UserValidatorService userValidatorService,
                                  RegisterUserDao registerUserDao, LocalMessage localMessage) {
        this.userValidatorService = userValidatorService;
        this.registerUserDao = registerUserDao;
        this.localMessage = localMessage;
    }

    public abstract Optional<Long> registerUser(RegistrationRequest registrationRequest);

    protected boolean isRequestValid(RegistrationRequest registrationRequest) {
        return userValidatorService.verifyRegistrationRequest(registrationRequest);
    }

    protected Optional<Long> attemptRegistration(RegistrationRequest registrationRequest) {
        if (checkUserNotExist(registrationRequest)) {
            return registerUserDao.saveUser(createUserFromRequest(registrationRequest));
        }
        return Optional.empty();
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

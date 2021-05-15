package com.wypychmat.rentals.rentapp.app.core.internationalization.registration;

import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationResponse;
import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationUserDto;
import com.wypychmat.rentals.rentapp.app.core.internationalization.MessageProviderCenter;
import com.wypychmat.rentals.rentapp.app.core.model.projection.UsernameEmail;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class RegistrationMessageProvider extends MessageProviderCenter {

    public RegistrationMessageProvider(MessageSource messageSource) {
        super(messageSource);
    }

    public RegistrationResponse getRegistrationResponse(RegistrationUserDto user, HttpStatus status) {
        return getResponse("register.wait.for.email",
                user.getEmail(),
                status,
                user.getId());
    }

    private RegistrationResponse getResponse(String messageKey,
                                             String userSpecificParam,
                                             HttpStatus httpStatus,
                                             long userId) {
        MessageFormat formatter =
                new MessageFormat(getLocalizedMessage(messageKey));
        String message = formatter.format(new Object[]{userSpecificParam});
        return new RegistrationResponse(httpStatus,
                userId,
                message);
    }

    public RegistrationResponse getConfirmationResponse(RegistrationUserDto user) {
        return getResponse("register.confirmed",
                user.getUsername(),
                HttpStatus.ACCEPTED,
                user.getId());
    }


    public Map<String, String> getRegistrationErrors(RegistrationRequest registrationRequest, UsernameEmail user) {
        String withGiven = getLocalizedMessage("error.user.with.given");
        String alreadyExist = getLocalizedMessage("error.already.exist");
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
        String baseMessage = getLocalizedMessage(messageKey);
        MessageFormat formatter = new MessageFormat(baseMessage);
        return formatter.format(new Object[]{withGiven, alreadyExist});
    }
}

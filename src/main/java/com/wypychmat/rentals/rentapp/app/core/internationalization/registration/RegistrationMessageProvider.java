package com.wypychmat.rentals.rentapp.app.core.internationalization.registration;

import com.wypychmat.rentals.rentapp.app.core.controller.dto.request.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.controller.dto.response.RegistrationResponse;
import com.wypychmat.rentals.rentapp.app.core.controller.dto.response.UserDto;
import com.wypychmat.rentals.rentapp.app.core.internationalization.MessageProviderCenter;
import com.wypychmat.rentals.rentapp.app.core.model.projection.UsernameEmail;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class RegistrationMessageProvider extends MessageProviderCenter {

    public RegistrationMessageProvider(MessageSource messageSource) {
        super(messageSource);
    }

    public RegistrationResponse getRegistrationResponse(UserDto user) {
        MessageFormat formatter =
                new MessageFormat(getLocalizedMessage("register.wait.for.email"));
        String message = formatter.format(new Object[]{user.getEmail()});
        return new RegistrationResponse(HttpStatus.CREATED,
                user.getId(),
                message);
    }

    public RegistrationResponse getConfirmationResponse(User user) {
        MessageFormat formatter =
                new MessageFormat(getLocalizedMessage("register.wait.for.email"));
        String message = formatter.format(new Object[]{user.getEmail()});
        return new RegistrationResponse(HttpStatus.CREATED,
                user.getId(),
                message);
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

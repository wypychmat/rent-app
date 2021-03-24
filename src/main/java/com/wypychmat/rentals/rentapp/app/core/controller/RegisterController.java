package com.wypychmat.rentals.rentapp.app.core.controller;


import com.wypychmat.rentals.rentapp.app.core.controller.dto.request.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.controller.dto.response.RegistrationResponse;
import com.wypychmat.rentals.rentapp.app.core.internationalization.LocalMessage;
import com.wypychmat.rentals.rentapp.app.core.service.user.RegistrationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;

@RestController
@RequestMapping("v1/api/register")
public class RegisterController {
    private final RegistrationServiceImpl registrationServiceImpl;
    private final LocalMessage localMessage;

    public RegisterController(RegistrationServiceImpl registrationServiceImpl, LocalMessage localMessage) {
        this.registrationServiceImpl = registrationServiceImpl;
        this.localMessage = localMessage;
    }

    @PostMapping
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest) throws IOException {
        Optional<Long> id = registrationServiceImpl.registerUser(registrationRequest);
        return id.map(item -> ResponseEntity.status(HttpStatus.CREATED)
                .body(getRegistrationResponse(item, registrationRequest.getEmail())))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }


    private RegistrationResponse getRegistrationResponse(Long item, String email) {
        MessageFormat formatter =
                new MessageFormat(localMessage.getLocalizedMessage("register.wait.for.email"));
        String message = formatter.format(new Object[]{email});
        return new RegistrationResponse(HttpStatus.CREATED,
                item,
                message);
    }
}

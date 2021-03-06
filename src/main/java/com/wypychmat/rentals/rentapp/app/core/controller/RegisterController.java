package com.wypychmat.rentals.rentapp.app.core.controller;


import com.wypychmat.rentals.rentapp.app.core.dto.registration.RefreshConfirmTokenRequest;
import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationResponse;
import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationUserDto;
import com.wypychmat.rentals.rentapp.app.core.internationalization.registration.RegistrationMessageProvider;
import com.wypychmat.rentals.rentapp.app.core.service.user.RegistrationService;
import com.wypychmat.rentals.rentapp.app.core.util.ApiVersion;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "${api.base}" + "${api.path.register.base}", produces = {ApiVersion.JSON, ApiVersion.V1_JSON})
public class RegisterController {
    private final RegistrationService registrationService;
    private final RegistrationMessageProvider messageProvider;

    public RegisterController(RegistrationService registrationService, MessageSource messageSource) {
        this.registrationService = registrationService;
        this.messageProvider = new RegistrationMessageProvider(messageSource);
    }

    @PostMapping
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        return getRegistrationResponse(registrationService.registerUser(registrationRequest),
                HttpStatus.CREATED);
    }

    private ResponseEntity<RegistrationResponse> getRegistrationResponse(Optional<RegistrationUserDto> user, HttpStatus status) {
        return user.map(item -> ResponseEntity.status(status)
                .body(messageProvider.getRegistrationResponse(item,status)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value = "/v1/" + "${api.path.register.confirm}", consumes = {ApiVersion.ANY})
    public ResponseEntity<RegistrationResponse> confirm(@RequestParam("${api.param.register.token}") String token) {

        RegistrationUserDto registrationUserDto = registrationService.confirmToken(token);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(messageProvider.getConfirmationResponse(registrationUserDto));
    }

    @PostMapping(path = "/" + "${api.path.register.refresh}")
    public ResponseEntity<RegistrationResponse> generateNewConfirmationToken(
            @RequestBody RefreshConfirmTokenRequest refreshConfirmTokenRequest) {

        return getRegistrationResponse(registrationService.refreshTokenForUser(refreshConfirmTokenRequest),
                HttpStatus.OK);
    }
}

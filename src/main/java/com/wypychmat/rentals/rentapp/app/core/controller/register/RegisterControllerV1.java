package com.wypychmat.rentals.rentapp.app.core.controller.register;


import com.wypychmat.rentals.rentapp.app.core.controller.register.dto.RefreshConfirmTokenRequest;
import com.wypychmat.rentals.rentapp.app.core.controller.register.dto.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.controller.register.dto.RegistrationResponse;
import com.wypychmat.rentals.rentapp.app.core.controller.register.dto.UserDto;
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
public class RegisterControllerV1 {
    private final RegistrationService registrationService;
    private final RegistrationMessageProvider messageProvider;

    public RegisterControllerV1(RegistrationService registrationService, MessageSource messageSource) {
        this.registrationService = registrationService;
        this.messageProvider = new RegistrationMessageProvider(messageSource);
    }

    @PostMapping
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest) {

        return getRegistrationResponse(registrationService.registerUser(registrationRequest),
                HttpStatus.CREATED);
    }

    private ResponseEntity<RegistrationResponse> getRegistrationResponse(Optional<UserDto> user, HttpStatus status) {

        return user.map(item -> ResponseEntity.status(status)
                .body(messageProvider.getRegistrationResponse(item,status)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value = "/v1/" + "${api.path.register.confirm}", consumes = {ApiVersion.ANY})
    public ResponseEntity<RegistrationResponse> confirm(@RequestParam("${api.param.register.token}") String token) {

        UserDto userDto = registrationService.confirmToken(token);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(messageProvider.getConfirmationResponse(userDto));
    }

    @PostMapping(path = "/" + "${api.path.register.refresh}")
    public ResponseEntity<RegistrationResponse> generateNewConfirmationToken(
            @RequestBody RefreshConfirmTokenRequest refreshConfirmTokenRequest) {

        return getRegistrationResponse(registrationService.refreshTokenForUser(refreshConfirmTokenRequest),
                HttpStatus.OK);
    }
}

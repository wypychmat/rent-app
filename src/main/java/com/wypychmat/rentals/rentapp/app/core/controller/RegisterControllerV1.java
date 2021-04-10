package com.wypychmat.rentals.rentapp.app.core.controller;


import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationResponse;
import com.wypychmat.rentals.rentapp.app.core.dto.UserDto;
import com.wypychmat.rentals.rentapp.app.core.internationalization.registration.RegistrationMessageProvider;
import com.wypychmat.rentals.rentapp.app.core.service.user.RegistrationService;
import com.wypychmat.rentals.rentapp.app.core.util.ApiVersion;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("${api.base}" + "${api.path.register}")
public class RegisterControllerV1 {
    private final RegistrationService registrationService;
    private final RegistrationMessageProvider messageProvider;

    public RegisterControllerV1(RegistrationService registrationService, MessageSource messageSource) {
        this.registrationService = registrationService;
        this.messageProvider = new RegistrationMessageProvider(messageSource);
    }

    @PostMapping(produces = {ApiVersion.JSON, ApiVersion.V1_JSON})
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        Optional<UserDto> user = registrationService.registerUser(registrationRequest);
        return user.map(item -> ResponseEntity.status(HttpStatus.CREATED)
                .body(messageProvider.getRegistrationResponse(item)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/v1/" + "${api.path.confirm}")
    public ResponseEntity<RegistrationResponse> confirm(@RequestParam("${api.param.register.token}") String token) {
        UserDto userDto = registrationService.confirmToken(token);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(messageProvider.getConfirmationResponse(userDto));
    }

}

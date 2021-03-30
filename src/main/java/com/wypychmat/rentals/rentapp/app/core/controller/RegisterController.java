package com.wypychmat.rentals.rentapp.app.core.controller;


import com.wypychmat.rentals.rentapp.app.core.controller.dto.request.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.controller.dto.response.RegistrationResponse;
import com.wypychmat.rentals.rentapp.app.core.controller.dto.response.UserDto;
import com.wypychmat.rentals.rentapp.app.core.internationalization.registration.RegistrationMessageProvider;
import com.wypychmat.rentals.rentapp.app.core.service.user.RegistrationService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("${api.base}" + "${api.path.register}")
public class RegisterController {
    private final RegistrationService registrationService;
    private final RegistrationMessageProvider messageProvider;

    public RegisterController(RegistrationService registrationService, MessageSource messageSource) {
        this.registrationService = registrationService;
        this.messageProvider = new RegistrationMessageProvider(messageSource);
    }

    @PostMapping("${api.version.newest}")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        Optional<UserDto> user = registrationService.registerUser(registrationRequest);
        return user.map(item -> ResponseEntity.status(HttpStatus.CREATED)
                .body(messageProvider.getRegistrationResponse(item)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());

    }

    @GetMapping("${api.version.newest}" + "/" + "${api.path.confirm}")
    public ResponseEntity<?> confirm(@RequestParam("${api.param.register.token}") String token) {
        registrationService.confirmToken(token);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}

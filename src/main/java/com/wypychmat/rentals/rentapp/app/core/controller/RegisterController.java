package com.wypychmat.rentals.rentapp.app.core.controller;


import com.wypychmat.rentals.rentapp.app.core.controller.dto.request.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.controller.dto.response.RegistrationResponse;
import com.wypychmat.rentals.rentapp.app.core.internationalization.LocalMessage;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import com.wypychmat.rentals.rentapp.app.core.service.user.RegistrationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.Optional;

@RestController
@RequestMapping("${api.base}" + "${api.path.register}")
public class RegisterController {
    private final RegistrationServiceImpl registrationServiceImpl;
    private final LocalMessage localMessage;

    public RegisterController(RegistrationServiceImpl registrationServiceImpl, LocalMessage localMessage) {
        this.registrationServiceImpl = registrationServiceImpl;
        this.localMessage = localMessage;
    }

    @PostMapping("${api.version.newest}")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        Optional<User> user = registrationServiceImpl.registerUser(registrationRequest);
        return user.map(item -> ResponseEntity.status(HttpStatus.CREATED)
                .body(getRegistrationResponse(item)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());

    }

    @GetMapping("${api.version.newest}" + "/" +"${api.path.confirm}")
    public String confirm(@RequestParam("${api.param.register.token}")String token) {
        String a = "a";
        System.out.println(token);
        return token;
    }


    private RegistrationResponse getRegistrationResponse(User user) {
        MessageFormat formatter =
                new MessageFormat(localMessage.getLocalizedMessage("register.wait.for.email"));
        String message = formatter.format(new Object[]{user.getEmail()});
        return new RegistrationResponse(HttpStatus.CREATED,
                user.getId(),
                message);
    }
}

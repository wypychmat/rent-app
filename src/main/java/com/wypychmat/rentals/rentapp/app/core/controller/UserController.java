package com.wypychmat.rentals.rentapp.app.core.controller;

import com.wypychmat.rentals.rentapp.app.core.dto.user.UserDto;
import com.wypychmat.rentals.rentapp.app.core.model.projection.user.UserWithRoles;
import com.wypychmat.rentals.rentapp.app.core.service.user.UsersManipulationService;
import com.wypychmat.rentals.rentapp.app.core.util.ApiVersion;
import com.wypychmat.rentals.rentapp.app.core.util.page.PageParam;
import com.wypychmat.rentals.rentapp.app.core.util.page.user.PageParamUsernameEmailEnabled;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "${api.base}" + "${api.path.users}", produces = {ApiVersion.JSON, ApiVersion.V1_JSON})
public class UserController {
    private final UsersManipulationService usersManipulationService;

    public UserController(UsersManipulationService usersManipulationService) {
        this.usersManipulationService = usersManipulationService;
    }

    @GetMapping
    @PostAuthorize("hasRole('ADMIN')")
    public Page<UserWithRoles> getUsers(
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String email,
            @RequestParam(defaultValue = "true") String enabled,
            @RequestParam(defaultValue = "0") String page,
            @RequestParam(defaultValue = "10") String size,
            @RequestParam(defaultValue = "id.asc", name = "sort") String[] orders) {

        return usersManipulationService.getAllUsers(
                PageParam.builder(PageParamUsernameEmailEnabled.Builder.class)
                        .setPage(page)
                        .setUsername(username)
                        .setSize(size)
                        .setEmail(email)
                        .setOrders(orders)
                        .setEnabled(enabled)
                        .build());
    }


    @GetMapping("/{id}")
    @PostAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<UserDto> user = usersManipulationService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}

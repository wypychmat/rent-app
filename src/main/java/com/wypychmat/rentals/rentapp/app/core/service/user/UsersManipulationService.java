package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.dto.user.UserDto;
import com.wypychmat.rentals.rentapp.app.core.model.projection.user.UserWithRoles;
import com.wypychmat.rentals.rentapp.app.core.util.page.user.PageParamUsernameEmailEnabled;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UsersManipulationService {

    Page<UserWithRoles> getAllUsers(PageParamUsernameEmailEnabled pageParamUsername);

    Optional<UserDto> getUserById(Long userId);

}

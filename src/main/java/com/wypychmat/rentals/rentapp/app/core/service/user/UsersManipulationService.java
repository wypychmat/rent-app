package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.model.projection.UserWithRoles;
import com.wypychmat.rentals.rentapp.app.core.util.page.user.PageParamUsernameEmailEnabled;
import org.springframework.data.domain.Page;

public interface UsersManipulationService {

    Page<UserWithRoles> getAllUsers(PageParamUsernameEmailEnabled pageParamUsername);



}

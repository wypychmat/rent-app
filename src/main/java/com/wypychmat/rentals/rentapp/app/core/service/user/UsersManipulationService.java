package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.model.projection.UserWithFlatRole;
import com.wypychmat.rentals.rentapp.app.core.util.page.PageParamUsername;
import org.springframework.data.domain.Page;

public interface UsersManipulationService {

    Page<UserWithFlatRole> getAllUsers(PageParamUsername pageParamUsername);

}

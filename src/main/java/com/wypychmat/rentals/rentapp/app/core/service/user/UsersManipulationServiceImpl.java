package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.model.projection.UserWithFlatRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class UsersManipulationServiceImpl implements UsersManipulationService {
    @Override
    public Page<UserWithFlatRole> getAllUsers(Pageable pageable) {
        return null;
    }
}

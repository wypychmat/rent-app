package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.model.projection.UserWithFlatRole;
import com.wypychmat.rentals.rentapp.app.core.repository.UserRepository;
import com.wypychmat.rentals.rentapp.app.core.service.PageableConverter;
import com.wypychmat.rentals.rentapp.app.core.util.page.PageParamUsername;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersManipulationServiceImpl implements UsersManipulationService {

    private final UserRepository userRepository;
    private final PageableConverter pageableConverter;

    public UsersManipulationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        pageableConverter = new PageableConverter();
    }

    @Override
    public Page<UserWithFlatRole> getAllUsers(PageParamUsername pageParamUsername) {
        Pageable pageable = pageableConverter.getPageableFromParam(pageParamUsername);
        return userRepository.getUserWithFlatRole(pageable);
    }

    @Override
    public List<Object> getFlat() {
        return userRepository.getFlat();
    }
}

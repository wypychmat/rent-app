package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.model.projection.UserWithRoles;
import com.wypychmat.rentals.rentapp.app.core.repository.UserRepository;
import com.wypychmat.rentals.rentapp.app.core.service.pagable.PageableConverter;
import com.wypychmat.rentals.rentapp.app.core.util.page.user.PageParamUsernameEmailEnabled;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsersManipulationServiceImpl implements UsersManipulationService {

    private final UserRepository userRepository;
    private final PageableConverter pageableConverter;

    public UsersManipulationServiceImpl(UserRepository userRepository, PageableConverter pageableConverter) {
        this.userRepository = userRepository;
        this.pageableConverter = pageableConverter;
    }

    @Override
    public Page<UserWithRoles> getAllUsers(PageParamUsernameEmailEnabled pageParam) {
        Pageable pageable = pageableConverter.getPageableFromParam(pageParam);
        return userRepository.getUserWithRoles(pageable,
                getContaining(pageParam.getUsername()),
                getContaining(pageParam.getEmail()),
                Boolean.parseBoolean(pageParam.isEnabled()));
    }

    private String getContaining(String param) {
        return "%" + param + "%";
    }


}

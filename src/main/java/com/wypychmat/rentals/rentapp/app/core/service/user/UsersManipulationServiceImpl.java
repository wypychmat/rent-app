package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.dto.user.UserDto;
import com.wypychmat.rentals.rentapp.app.core.mapper.UserMapper;
import com.wypychmat.rentals.rentapp.app.core.model.projection.UserProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.UserWithRoles;
import com.wypychmat.rentals.rentapp.app.core.repository.UserRepository;
import com.wypychmat.rentals.rentapp.app.core.service.pagable.PageableConverter;
import com.wypychmat.rentals.rentapp.app.core.util.page.user.PageParamUsernameEmailEnabled;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersManipulationServiceImpl implements UsersManipulationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PageableConverter pageableConverter;

    public UsersManipulationServiceImpl(UserRepository userRepository,
                                        UserMapper userMapper,
                                        PageableConverter pageableConverter) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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

    @Override
    public Optional<UserDto> getUserById(Long userId) {
        Optional<UserProjection> user = userRepository.findUserByIdWithProjection(userId);
        return user.map(userProjection -> userMapper.toUserDtoFromProjection(
                userProjection,
                userRepository.findUserRolesByUserId(userId)));
    }

    private String getContaining(String param) {
        return "%" + param + "%";
    }


}

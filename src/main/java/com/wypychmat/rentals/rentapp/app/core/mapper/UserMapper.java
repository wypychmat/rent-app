package com.wypychmat.rentals.rentapp.app.core.mapper;

import com.wypychmat.rentals.rentapp.app.core.dto.user.UserDto;
import com.wypychmat.rentals.rentapp.app.core.model.projection.user.UserProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface UserMapper {

    @Mapping(source = "roles", target = "roles")
    UserDto toUserDtoFromProjection(UserProjection userProjection, List<String> roles);
}

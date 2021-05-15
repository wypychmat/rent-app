package com.wypychmat.rentals.rentapp.app.core.mapper;


import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationUserDto;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RegistrationMapper {

    RegistrationUserDto userToRegistration (User user);

    @Mapping(target = "userRoles", ignore = true)
    @Mapping(target = "registerTokens", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", constant = "false")
    User registrationRequestToUser(RegistrationRequest registrationRequest);


}

package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.exception.InvalidUserRequestException;
import com.wypychmat.rentals.rentapp.app.core.model.projection.UsernameEmail;
import com.wypychmat.rentals.rentapp.app.core.model.user.RegisterToken;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;

import java.util.Optional;

interface RegisterUserDao {

    Optional<UsernameEmail> existByUsernameAndEmail(String username, String email) throws InvalidUserRequestException;

    Optional<User> saveUser(User user);

    Optional<RegisterToken> saveToken(RegisterToken registerToken);


}

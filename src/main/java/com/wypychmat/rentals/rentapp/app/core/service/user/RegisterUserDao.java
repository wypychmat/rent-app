package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.dto.registration.RefreshConfirmTokenRequest;
import com.wypychmat.rentals.rentapp.app.core.exception.register.InvalidUserRequestException;
import com.wypychmat.rentals.rentapp.app.core.model.projection.user.UsernameEmail;
import com.wypychmat.rentals.rentapp.app.core.model.user.RegisterToken;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;

import java.util.Optional;

interface RegisterUserDao {

    Optional<UsernameEmail> existByUsernameAndEmail(String username, String email) throws InvalidUserRequestException;

    User registerUser(User user, RegisterToken registerToken);

    Optional<RegisterToken> saveToken(RegisterToken registerToken);

    Optional<RegisterToken> findToken(String token);

    void deleteUserByUsername(String username);

    void enableUserById(Long id);

    void deleteTokenExpectGiven(RegisterToken registerToken, long userId);

    Optional<User> getUserWhenUserExistByUsernameAndEmail(RefreshConfirmTokenRequest refreshConfirmTokenRequest);
}

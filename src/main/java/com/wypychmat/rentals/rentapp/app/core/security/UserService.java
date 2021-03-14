package com.wypychmat.rentals.rentapp.app.core.security;

import java.util.Optional;

interface UserService {
    Optional<UserDetailsModel> getUserByUsername(String username);
}

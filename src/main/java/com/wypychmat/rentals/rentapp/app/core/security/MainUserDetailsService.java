package com.wypychmat.rentals.rentapp.app.core.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@UserDetailsServiceSelector
class MainUserDetailsService implements UserDetailsService {

    private final UserService userService;

    MainUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userService.getUserByUsername(s).orElseThrow(() -> {
            throw new UsernameNotFoundException(String.format("User: \"%s\" not found", s));
        });
    }
}

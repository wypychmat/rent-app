package com.wypychmat.rentals.rentapp.app.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CheckUser {
    public final boolean canAccess(Authentication authentication, Long userId) {
        if (userId == null)
            return true;
        long currentUser = Long.parseLong(authentication.getDetails().toString());
        return userId.equals(currentUser);
    }
}

package com.wypychmat.rentals.rentapp.app.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("checkUser")
public class Check {
    // authentication is injected    userId is pathVariable
//@PreAuthorize("hasRole('admin') or @checkUser.checkIsRightUser(authentication,#userId)")
    public final boolean checkIsRightUser(Authentication authentication, Long userId) {
        // do your check(s) here
        String a= "a";
        return true;
    }
}

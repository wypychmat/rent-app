package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AddUserIdAspect {

    @Before(value = "addUserPointcut(appUser)", argNames = "appUser")
    public void userAdvice(AppUser appUser) {
        appUser.userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getDetails().toString());
    }

    @Pointcut("within(com.wypychmat.rentals.rentapp.app.core.service.vehicle.RentService+)" +
            " && execution(* *(com.wypychmat.rentals.rentapp.app.core.service.vehicle.AddUserIdAspect.AppUser,..)) " +
            "&& args(appUser,..)")
    public void addUserPointcut(AppUser appUser) {
    }

    public static class AppUser {

        private Long userId;

        public AppUser() {
        }

        public Long getUserId() {
            return userId;
        }
    }

}

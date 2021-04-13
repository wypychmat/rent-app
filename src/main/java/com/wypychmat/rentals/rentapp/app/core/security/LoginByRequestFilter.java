package com.wypychmat.rentals.rentapp.app.core.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class LoginByRequestFilter extends UsernamePasswordAuthenticationFilter {
    private final LoginService loginService;
    private final AuthenticationManager authenticationManager;

    LoginByRequestFilter(LoginFilterDependency loginFilterDependency) {
        loginService = new LoginService(loginFilterDependency);
        authenticationManager = loginFilterDependency.getAuthenticationManager();
        setRequiresAuthenticationRequestMatcher
                (new AntPathRequestMatcher(loginFilterDependency.getLoginPath(), "POST"));
        setAuthenticationSuccessHandler(loginService.getAuthenticationSuccessHandler());
        setAuthenticationFailureHandler(
                loginService.getAuthenticationFailureHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        LoginRequest loginRequest = loginService.getLoginRequest(request);
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    }


}

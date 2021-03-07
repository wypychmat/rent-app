package com.wypychmat.rentals.rentapp.app.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

class AuthByRequestFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    AuthByRequestFilter(AuthenticationManager authenticationManager) {
        objectMapper = new ObjectMapper();
        this.authenticationManager = authenticationManager;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/v1/api/login", "POST"));
        setAuthenticationSuccessHandler((rq, rs, e) -> createResponse(rs, "OK", HttpServletResponse.SC_OK));
        setAuthenticationFailureHandler((rq, rs, e) -> createResponse(rs, "FAIL", HttpServletResponse.SC_UNAUTHORIZED));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "",
                ""
        );
        try {
            LoginRequest authenticationRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );
        } catch (IOException e) {
            String message = e.getMessage();
            if (message.contains("Unrecognized field")) {
                createResponse(response, "Unrecognized payload!", HttpServletResponse.SC_UNAUTHORIZED);
            }
            e.printStackTrace();
        } catch (IncorrectPayloadException e) {
            e.printStackTrace();
            createResponse(response, e.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
        }
        return authenticationManager.authenticate(authentication);
    }

    private void createResponse(HttpServletResponse response, String message, int status) {
        try {
//        response.addHeader("Content-Type", "application/json");
            response.setStatus(status);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            objectMapper.writeValue(response.getWriter(), message);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}

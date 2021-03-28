package com.wypychmat.rentals.rentapp.app.core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

// TODO: 27.03.2021 change messages provider to Validator
class AuthByRequestFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final JwtConfig jwtConfig;
    private final ZoneId zoneId;

    AuthByRequestFilter(AuthenticationManager authenticationManager,Algorithm algorithm, JwtConfig jwtConfig,
                        String loginPath) {
        this.jwtConfig = jwtConfig;
        objectMapper = new ObjectMapper();
        this.authenticationManager = authenticationManager;
        zoneId = ZoneId.systemDefault();
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(loginPath, "POST"));
        setAuthenticationSuccessHandler((rq, rs, a) -> {
            LocalDateTime localDateTime = LocalDateTime.now();
            Date nowDate = Date.from(localDateTime.atZone(zoneId).toInstant());
            Date expiresAt = Date.from(localDateTime.plusMinutes(jwtConfig.getExpirationInMin())
                    .atZone(zoneId).toInstant());
            String[] authorities = a.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toArray(String[]::new);
            String token = JWT.create().withIssuer(a.getName())
                    .withArrayClaim(jwtConfig.getAuthorities(), authorities)
                    .withIssuedAt(nowDate)
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);
            createResponse(rs, new LoginOkResponse(HttpServletResponse.SC_OK,
                    nowDate,
                    expiresAt,
                    jwtConfig.getPrefix() + token));
        });
        setAuthenticationFailureHandler((rq, rs, a) -> createResponse(rs,
                new LoginErrorResponse(HttpServletResponse.SC_UNAUTHORIZED,
                Date.from(LocalDateTime.now().atZone(zoneId).toInstant()),
                "Invalid username or password"
        )));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
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
            createResponse(response, new LoginErrorResponse(HttpServletResponse.SC_UNAUTHORIZED,
                    Date.from(LocalDateTime.now().atZone(zoneId).toInstant()),
                    "Unrecognized payload!"
            ));
            e.printStackTrace();
        } catch (IncorrectPayloadException e) {
            e.printStackTrace();
            createResponse(response, new LoginErrorResponse(HttpServletResponse.SC_UNAUTHORIZED,
                    Date.from(LocalDateTime.now().atZone(zoneId).toInstant()),
                    e.getMessage()
            ));
        }
        return authenticationManager.authenticate(authentication);
    }

    private void createResponse(HttpServletResponse response, LoginResponse loginResponse) {
        try {
            response.addHeader("Content-Type", jwtConfig.getContentType());
            response.setStatus(loginResponse.getStatus());
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            objectMapper.writeValue(response.getWriter(), loginResponse);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}

package com.wypychmat.rentals.rentapp.app.core.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


// TODO: 13.04.2021 add message from properties
class LoginService {

    private final LoginFilterDependency dependency;
    private final ObjectMapper objectMapper;
    private final ZoneId zoneId;
    private final LoginRequestVerifier loginRequestVerifier;

    public LoginService(LoginFilterDependency dependency) {
        this.dependency = dependency;
        this.objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.zoneId = ZoneId.systemDefault();
        this.loginRequestVerifier = new LoginRequestVerifier(this.dependency.getValidator());
    }


    LoginRequest getLoginRequest(HttpServletRequest request) {
        try {
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            loginRequestVerifier.validateLoginRequest(loginRequest);
            return loginRequest;
        } catch (UnrecognizedPropertyException e) {
            throw new BadRequestAuthException(
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    getErrorsForUnrecognizedPayload(e));
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestAuthException("Unrecognized payload!");
        }
    }

    private Map<String, String> getErrorsForUnrecognizedPayload(UnrecognizedPropertyException e) throws BadRequestAuthException {
        String propertyName = e.getPropertyName();
        String knowProperties = e.getKnownPropertyIds().stream().map(Object::toString)
                .reduce("", (previous, next) ->
                        previous.equals("") ? next : previous + ", " + next
                );
        HashMap<String, String> errors = new HashMap<>();
        errors.put("unknownProperty", propertyName);
        errors.put("knowProperties", knowProperties);
        return errors;
    }

    AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
        return (rq, rs, auth) -> {
            JwtConfig jwtConfig = dependency.getJwtConfig();
            LocalDateTime localDateTime = LocalDateTime.now();
            Date nowDate = Date.from(localDateTime.atZone(zoneId).toInstant());
            Date expiresAt = Date.from(localDateTime.plusMinutes(jwtConfig.getExpirationInMin())
                    .atZone(zoneId).toInstant());
            String token = getToken(auth, nowDate, expiresAt);
            createResponse(rs, new LoginOkResponse(HttpStatus.OK,
                    nowDate,
                    expiresAt,
                    jwtConfig.getPrefix() + token));
        };
    }

    private String getToken(Authentication auth, Date nowDate, Date expiresAt) {
        return JWT.create().withIssuer(auth.getName())
                .withClaim(dependency.getJwtConfig().getAuthorities(), getAuthorities(auth))
                .withIssuedAt(nowDate)
                .withExpiresAt(expiresAt)
                .sign(dependency.getAlgorithm());
    }

    private List<String> getAuthorities(Authentication auth) {
        return auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return (rq, rs, ex) -> createResponse(rs,
                getErrorResponseBody(ex));
    }

    private LoginErrorResponse getErrorResponseBody(AuthenticationException ex) {
        if (ex instanceof BadRequestAuthException) {
            return new LoginErrorResponse(HttpStatus.BAD_REQUEST,
                    getIssuedAt(),
                    ((BadRequestAuthException) ex).getErrors()
            );
        }
        return getErrorMessage(ex);
    }

    private LoginErrorResponse getErrorMessage(AuthenticationException ex) {
        String message = "Invalid username or password";
        if (ex instanceof DisabledException)
            message = "Please confirm email to fully activated account";
        return new LoginErrorResponse(HttpStatus.UNAUTHORIZED,
                getIssuedAt(),
                message
        );
    }

    private void createResponse(HttpServletResponse response, LoginResponse loginResponse) throws IOException {
        response.addHeader(HttpHeaders.CONTENT_TYPE, dependency.getJwtConfig().getContentType());
        response.setStatus(loginResponse.getStatus());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        objectMapper.writeValue(response.getWriter(), loginResponse);
    }

    private Date getIssuedAt() {
        return Date.from(LocalDateTime.now().atZone(zoneId).toInstant());
    }
}

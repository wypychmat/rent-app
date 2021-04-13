package com.wypychmat.rentals.rentapp.app.core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.wypychmat.rentals.rentapp.app.core.util.Constant.*;

// TODO: 27.03.2021 change messages provider to Validator
class RequestTokenFilter extends OncePerRequestFilter {
    private final AuthFilterDependency dependency;


    RequestTokenFilter(AuthFilterDependency authFilterDependency) {
        this.dependency = authFilterDependency;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && !header.isEmpty() && !header.isBlank() && header.startsWith(
                dependency.getJwtConfig().getPrefix())) {
            attemptToAuthentication(request, header);
        }
        filterChain.doFilter(request, response);
    }

    private void attemptToAuthentication(HttpServletRequest request, String header) {
        try {
            String token = header.substring(dependency.getJwtConfig().getPrefix().length());
            JWTVerifier verifier = JWT.require(dependency.getAlgorithm()).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            Map<String, Claim> claims = decodedJWT.getClaims();
            Claim username = claims.get("iss");
            Claim claimAuthorities = claims.get("authorities");
            List<String> authorities = claimAuthorities.asList(String.class);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities.stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toSet())));
        } catch (TokenExpiredException e) {
            request.setAttribute(CUSTOM_ERROR_MESSAGE, e.getMessage());
            e.printStackTrace();
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            request.setAttribute(CUSTOM_ERROR_MESSAGE, "Invalid signature");
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            request.setAttribute(CUSTOM_ERROR_MESSAGE, "Cannot verify token");
        }
    }
}

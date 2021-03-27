package com.wypychmat.rentals.rentapp.app.core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
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
import java.util.Set;
import java.util.stream.Collectors;

class RequestTokenFilter extends OncePerRequestFilter {
    private final JwtConfig jwtConfig;
    private final Algorithm algorithm;

    RequestTokenFilter(JwtConfig jwtConfig, Algorithm algorithm) {
        this.jwtConfig = jwtConfig;
        this.algorithm = algorithm;
    }

    // TODO: 27.03.2021 change messages provider to Validator

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && !header.isEmpty() && !header.isBlank() && header.startsWith(jwtConfig.getPrefix())) {
            try {
                String token = header.substring(jwtConfig.getPrefix().length());
                JWTVerifier verifier = JWT.require(algorithm).build();
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
                request.setAttribute("customErrorMessage",e.getMessage());
                e.printStackTrace();
            } catch (SignatureVerificationException e) {
                e.printStackTrace();
                request.setAttribute("customErrorMessage","Invalid signature");
            }
            catch (JWTVerificationException e) {
                e.printStackTrace();
                request.setAttribute("customErrorMessage","Cannot verify token");
            }
        }
        filterChain.doFilter(request, response);
    }
}

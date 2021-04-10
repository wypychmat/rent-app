package com.wypychmat.rentals.rentapp.app.core.security;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableConfigurationProperties({RsaKeyConfig.class, JwtConfig.class})
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JwtConfig jwtConfig;
    private final Algorithm algorithm;
    private final LoginRegisterPath loginRegisterPath;


    @Autowired
    SecurityConfiguration(PasswordEncoder passwordEncoder,
                          @UserDetailsServiceSelector UserDetailsService userDetailsService,
                          @AuthEntryPoint AuthenticationEntryPoint authenticationEntryPoint,
                          RsaKeyConfig rsaKeyConfig,
                          JwtConfig jwtConfig, LoginRegisterPath loginRegisterPath) {

        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtConfig = jwtConfig;
        this.loginRegisterPath = loginRegisterPath;
        KeyProvider keyProvider = new KeyProvider(rsaKeyConfig);
        this.algorithm = Algorithm.RSA256(keyProvider.getPublicKey(), keyProvider.getPrivateKey());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .disable() // for Postman
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new LoginByRequestFilter(authenticationManager(), algorithm, jwtConfig,
                                loginRegisterPath.getMatcherLoginPath()),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new RequestTokenFilter(jwtConfig, algorithm), LoginByRequestFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, loginRegisterPath.getMatcherRegisterPath())
                .permitAll()
                .regexMatchers(HttpMethod.GET,loginRegisterPath.getRegexForConfirmPath())
                .permitAll()
                .anyRequest()
                .authenticated();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
//        auth.userDetailsService(applicationUserService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }


}

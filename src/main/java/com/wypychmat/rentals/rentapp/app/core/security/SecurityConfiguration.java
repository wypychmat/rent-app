package com.wypychmat.rentals.rentapp.app.core.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.wypychmat.rentals.rentapp.app.core.internationalization.MessageProviderCenter;
import com.wypychmat.rentals.rentapp.app.core.internationalization.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.validation.Validator;


@Configuration
@EnableWebSecurity
@EnableConfigurationProperties({RsaKeyConfig.class, JwtConfig.class})
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final LoginRegisterPath loginRegisterPath;
    private final AuthFilterDependency authFilterDependency;
    private final LoginFilterDependency loginFilterDependency;


    @Autowired
    SecurityConfiguration(PasswordEncoder passwordEncoder,
                          @UserDetailsServiceSelector UserDetailsService userDetailsService,
                          RsaKeyConfig rsaKeyConfig,
                          JwtConfig jwtConfig, LoginRegisterPath loginRegisterPath,
                          Validator validator, MessageProviderCenter messageProviderCenter) {

        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.loginRegisterPath = loginRegisterPath;
        KeyProvider keyProvider = new KeyProvider(rsaKeyConfig);
        authFilterDependency = new AuthFilterDependency(
                messageProviderCenter,
                Algorithm.RSA256(keyProvider.getPublicKey(), keyProvider.getPrivateKey()),
                jwtConfig);
        loginFilterDependency = LoginFilterDependency.from(authFilterDependency,
                loginRegisterPath.getMatcherLoginPath(),
                validator);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        loginFilterDependency.setAuthenticationManager(authenticationManager());
        http
                .csrf()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .disable() // for Postman
                .exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPointHandler())
                .accessDeniedHandler(new CustomAccessDeniedHandler()).and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new LoginByRequestFilter(loginFilterDependency),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new RequestTokenFilter(authFilterDependency), LoginByRequestFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, loginRegisterPath.getMatcherRegisterPath())
                .permitAll()
                .antMatchers(HttpMethod.POST, loginRegisterPath.getMatcherRegisterRefreshPath()).permitAll()
                .regexMatchers(HttpMethod.GET, loginRegisterPath.getRegexForConfirmPath())
                .permitAll()
                .anyRequest()
                .authenticated();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }


    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

}

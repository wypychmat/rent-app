package com.wypychmat.rentals.rentapp.app.core.service.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;


@Configuration
class ValidatorConfiguration {

    @Bean
    Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}

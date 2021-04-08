package com.wypychmat.rentals.rentapp.app.core.validation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;


@Configuration
public class ValidatorProvider {

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}

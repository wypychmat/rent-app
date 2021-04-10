package com.wypychmat.rentals.rentapp.app.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;


@Configuration
public class ValidatorProvider {

    private final MessageSource messageSource;

    public ValidatorProvider(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Bean
    public Validator validator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }
}

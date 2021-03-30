package com.wypychmat.rentals.rentapp.app.core.service.user;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

public class ValidationBase {
    private static ValidatorFactory validatorFactory;
    protected static UserValidatorServiceImpl userValidatorServiceImpl;

    @BeforeAll
    static void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        userValidatorServiceImpl = new UserValidatorServiceImpl(validatorFactory.getValidator());
    }

    @AfterAll
    static void tearDown(){
        validatorFactory.close();
    }
}

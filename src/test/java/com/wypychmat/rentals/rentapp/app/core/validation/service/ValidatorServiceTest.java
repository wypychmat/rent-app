package com.wypychmat.rentals.rentapp.app.core.validation.service;

import com.wypychmat.rentals.rentapp.app.core.controller.dto.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidRegisterRequestException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ValidatorServiceTest {
    private static ValidatorFactory validatorFactory;
    private static ValidatorService validatorService;

    @BeforeAll
    static void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validatorService = new ValidatorService(validatorFactory.getValidator());
    }

    @AfterAll
    static void tearDown(){
        validatorFactory.close();
    }


    @ParameterizedTest(name = "should thrown {6} error when username={0}; password={1}; rePassword={2}; email={3};" +
            " firstname={4}; lastname={5}")
    @CsvFileSource(resources = "/registerRequest.csv", numLinesToSkip = 1)
    void shouldThrowInvalidRegisterRequestExceptionFromFile(String username,
                                                            String password,
                                                            String rePassword,
                                                            String email,
                                                            String firstname,
                                                            String lastname,
                                                            int expectedErrors) {
        //given
        RegistrationRequest registrationRequest = new
                RegistrationRequest(username,
                password,
                rePassword,
                email,
                firstname,
                lastname);
        //when
        Executable verificationServiceRun = () -> validatorService
                .verifyRegistrationRequest(registrationRequest);

        // then
        InvalidRegisterRequestException invalidRegisterRequestException =
                assertThrows(InvalidRegisterRequestException.class, verificationServiceRun);
        assertThat(invalidRegisterRequestException.getErrors().size()).isEqualTo(expectedErrors);
    }

    @Test
    void shouldPassWhenRegistrationRequestIsValid(){
        //given
        RegistrationRequest registrationRequest = new
                RegistrationRequest("username",
                "password1@D",
                "password1@D",
                "email@email.com",
                "firstname",
                "lastname");
        //when
        boolean verifyRequest = validatorService.verifyRegistrationRequest(registrationRequest);

        //then
        assertThat(verifyRequest).isTrue();
    }

}
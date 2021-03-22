package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.controller.dto.request.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidUserRequestException;
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


class UserValidatorServiceImplTest extends ValidationBase{

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
        Executable verificationServiceRun = () -> userValidatorServiceImpl
                .verifyRegistrationRequest(registrationRequest);

        // then
        InvalidUserRequestException invalidUserRequestException =
                assertThrows(InvalidUserRequestException.class, verificationServiceRun);
        assertThat(invalidUserRequestException.getErrors().size()).isEqualTo(expectedErrors);
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
        boolean verifyRequest = userValidatorServiceImpl.verifyRegistrationRequest(registrationRequest);

        //then
        assertThat(verifyRequest).isTrue();
    }

}
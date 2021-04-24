package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.TestContainerBaseWithEmail;
import com.wypychmat.rentals.rentapp.app.core.controller.register.dto.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.controller.register.dto.RegistrationUserDto;
import com.wypychmat.rentals.rentapp.app.core.exception.register.InvalidUserRequestException;
import com.wypychmat.rentals.rentapp.app.core.mapper.RegistrationMapper;
import com.wypychmat.rentals.rentapp.app.core.service.mail.SimpleEmailMessageService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.*;


class RegistrationServiceTest extends TestContainerBaseWithEmail {
    public static final String EMAIL_COM = "@email.com";
    private static RegistrationService registrationServiceWithoutEmailSending;
    private static RegisterUserDao registerUserDao;
    private static AtomicInteger userSpecific;

    private static final SimpleEmailMessageService emailService = mock(SimpleEmailMessageService.class);


    // TODO: 29.03.2021
    @BeforeAll
    static void setUp(@Autowired UserValidatorService userValidatorService,
                      @Autowired RegisterUserDao registerUserDao,
                      @Autowired MessageSource messageSource,
                      @Autowired RegistrationMapper registrationMapper) {
        RegistrationServiceTest.registerUserDao = registerUserDao;
        userSpecific = new AtomicInteger(0);
        registrationServiceWithoutEmailSending = new RegistrationServiceTestImplementation(userValidatorService,
                registerUserDao,
                emailService,
                messageSource,
                registrationMapper);
    }


    @Test
    void shouldRegisterUserWhenNew() {
        //given
        String username = "username" + userSpecific.incrementAndGet();
        RegistrationRequest registrationRequest = getValidRegistrationRequest(username, username);
        //when
        registerUserDao.deleteUserByUsername(username);
        Optional<RegistrationUserDto> user = registrationServiceWithoutEmailSending.registerUser(registrationRequest);
        //then
        assertThat(user).isPresent();

        verify(emailService, times(1))
                .sendEmail(new RegistrationMessagePayload(username, username + EMAIL_COM, any()));
    }

    @Test
    void shouldNotRegisterUserWhenUsernameAndEmailExist() {
        //given
        String username = "username" + userSpecific.incrementAndGet();
        RegistrationRequest registrationRequest = getValidRegistrationRequest(username,
                username);

        RegistrationRequest secondRegistrationRequest = getValidRegistrationRequest(username, username);

        Optional<RegistrationUserDto> firstResult = registrationServiceWithoutEmailSending.registerUser(registrationRequest);

        assertThat(firstResult).isPresent();
        assertThatExceptionOfType(InvalidUserRequestException.class)
                .isThrownBy(() -> registrationServiceWithoutEmailSending.registerUser(secondRegistrationRequest));
    }

    @Test
    void shouldNotRegisterUserWhenEmailExist() {
        //given
        int firstId = userSpecific.incrementAndGet();
        int secondId = userSpecific.incrementAndGet();
        String firstUsername = "username" + firstId;
        String secondUsername = "username" + secondId;
        RegistrationRequest firstRegistrationRequest = getValidRegistrationRequest(firstUsername, firstUsername);

        RegistrationRequest secondRegistrationRequest = getValidRegistrationRequest(secondUsername, firstUsername);
        //when then
        Optional<RegistrationUserDto> firstResult = registrationServiceWithoutEmailSending.registerUser(firstRegistrationRequest);

        assertThat(firstResult).isPresent();
        assertThatExceptionOfType(InvalidUserRequestException.class)
                .isThrownBy(() -> registrationServiceWithoutEmailSending.registerUser(secondRegistrationRequest));
    }


    @Test
    void shouldNotRegisterUserWhenUsernameExist() {
        //given
        int firstId = userSpecific.incrementAndGet();
        int secondId = userSpecific.incrementAndGet();
        String firstUsername = "username" + firstId;
        String secondUsername = "username" + secondId;
        RegistrationRequest firstRegistrationRequest = getValidRegistrationRequest(firstUsername, firstUsername);

        RegistrationRequest secondRegistrationRequest = getValidRegistrationRequest(firstUsername, secondUsername);
        //when then
        Optional<RegistrationUserDto> firstResult = registrationServiceWithoutEmailSending.registerUser(firstRegistrationRequest);

        assertThat(firstResult).isPresent();
        assertThatExceptionOfType(InvalidUserRequestException.class)
                .isThrownBy(() -> registrationServiceWithoutEmailSending.registerUser(secondRegistrationRequest));
    }

    @Test
    void shouldNotRegisterUserWhenInvalidRequest() {
        //given
        String username = "username" + userSpecific.incrementAndGet();
        RegistrationRequest registrationRequest = getInvalidRegistrationRequest(username, username);

        //when then

        assertThatExceptionOfType(InvalidUserRequestException.class)
                .isThrownBy(() -> registrationServiceWithoutEmailSending.registerUser(registrationRequest));
    }

    private RegistrationRequest getValidRegistrationRequest(String username, String email) {
        return new
                RegistrationRequest(username,
                "password1@D",
                "password1@D",
                email + EMAIL_COM,
                "firstname",
                "lastname");
    }

    private RegistrationRequest getInvalidRegistrationRequest(String username, String email) {
        return new
                RegistrationRequest(username,
                "password1@Da",
                "password1@D",
                email,
                "a",
                "a");
    }
}
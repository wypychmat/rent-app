package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.TestContainerBase;
import com.wypychmat.rentals.rentapp.app.core.controller.dto.request.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.controller.dto.response.UserDto;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidUserRequestException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class RegistrationServiceImplTestWithoutEmailSending extends TestContainerBase {
    private static RegistrationService<MimeMessage> registrationServiceWithoutEmailSending;
    private static RegisterUserDao registerUserDao;
    private static AtomicInteger userSpecific;

    private static final MimeEmailService emailService = mock(MimeEmailService.class);

    @BeforeAll
    static void setUp(@Autowired UserValidatorService userValidatorService,
                      @Autowired RegisterUserDao registerUserDao,
                      @Autowired MessageSource messageSource) {
        RegistrationServiceImplTestWithoutEmailSending.registerUserDao = registerUserDao;
        userSpecific = new AtomicInteger(0);
        when(emailService.send()).thenReturn(mimeMessage -> Optional.empty());
        when(emailService.getResourceString()).thenReturn(Optional.of("message"));
        registrationServiceWithoutEmailSending = new RegistrationServiceImpl(userValidatorService,
                registerUserDao,
                emailService,
                messageSource);
    }


    @Test
    void shouldRegisterUserWhenNew() {
        //given
        String username = "username" + userSpecific.incrementAndGet();
        RegistrationRequest registrationRequest = getValidRegistrationRequest(username, username);
        //when
        registerUserDao.deleteUserByUsername(username);
        Optional<UserDto> user = registrationServiceWithoutEmailSending.registerUser(registrationRequest);
        //then
        assertThat(user).isPresent();
    }

    @Test
    void shouldNotRegisterUserWhenUsernameAndEmailExist() {
        //given
        String username = "username" + userSpecific.incrementAndGet();
        RegistrationRequest registrationRequest = getValidRegistrationRequest(username,
                username);

        RegistrationRequest secondRegistrationRequest = getValidRegistrationRequest(username, username);
        //when then
//        when(mockService.send()).
        Optional<UserDto> firstResult = registrationServiceWithoutEmailSending.registerUser(registrationRequest);

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
        Optional<UserDto> firstResult = registrationServiceWithoutEmailSending.registerUser(firstRegistrationRequest);

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
        Optional<UserDto> firstResult = registrationServiceWithoutEmailSending.registerUser(firstRegistrationRequest);

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
                email + "@email.com",
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
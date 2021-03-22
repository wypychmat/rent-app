package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.TestContainerBase;
import com.wypychmat.rentals.rentapp.app.core.controller.dto.request.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidUserRequestException;
import com.wypychmat.rentals.rentapp.app.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class RegistrationServiceImplTest extends TestContainerBase {
    private static RegistrationService registrationService;
    private static UserRepository userRepository;
    private static AtomicInteger userSpecific;

    @BeforeAll
    static void setUp(@Autowired RegistrationService registrationService, @Autowired UserRepository userRepository) {
        RegistrationServiceImplTest.registrationService = registrationService;
        RegistrationServiceImplTest.userRepository = userRepository;
        userSpecific = new AtomicInteger(0);
    }


    @Test
    @Transactional
    void shouldRegisterUserWhenNew() {
        //given
        String username = "username" + userSpecific.incrementAndGet();
        RegistrationRequest registrationRequest = getValidRegistrationRequest(username, username);
        //when
        userRepository.deleteUserByUsername(username);
        Optional<Long> id = registrationService.registerUser(registrationRequest);
        //then
        assertThat(id).isPresent();
    }

    @Test
    @Transactional
    void shouldNotRegisterUserWhenUsernameAndEmailExist() {
        //given
        String username = "username" + userSpecific.incrementAndGet();
        RegistrationRequest registrationRequest = getValidRegistrationRequest(username,
                username);

        RegistrationRequest secondRegistrationRequest = getValidRegistrationRequest(username, username);
        //when then
        Optional<Long> firstResult = registrationService.registerUser(registrationRequest);

        assertThat(firstResult).isPresent();
        assertThatExceptionOfType(InvalidUserRequestException.class)
                .isThrownBy(() -> registrationService.registerUser(secondRegistrationRequest));
    }

    @Test
    @Transactional
    void shouldNotRegisterUserWhenEmailExist() {
        //given
        int firstId = userSpecific.incrementAndGet();
        int secondId = userSpecific.incrementAndGet();
        String firstUsername = "username" + firstId;
        String secondUsername = "username" + secondId;
        RegistrationRequest firstRegistrationRequest = getValidRegistrationRequest(firstUsername, firstUsername);

        RegistrationRequest secondRegistrationRequest = getValidRegistrationRequest(secondUsername, firstUsername);
        //when then
        Optional<Long> firstResult = registrationService.registerUser(firstRegistrationRequest);

        assertThat(firstResult).isPresent();
        assertThatExceptionOfType(InvalidUserRequestException.class)
                .isThrownBy(() -> registrationService.registerUser(secondRegistrationRequest));
    }


    @Test
    @Transactional
    void shouldNotRegisterUserWhenUsernameExist() {
        //given
        int firstId = userSpecific.incrementAndGet();
        int secondId = userSpecific.incrementAndGet();
        String firstUsername = "username" + firstId;
        String secondUsername = "username" + secondId;
        RegistrationRequest firstRegistrationRequest = getValidRegistrationRequest(firstUsername, firstUsername);

        RegistrationRequest secondRegistrationRequest = getValidRegistrationRequest(firstUsername, secondUsername);
        //when then
        Optional<Long> firstResult = registrationService.registerUser(firstRegistrationRequest);

        assertThat(firstResult).isPresent();
        assertThatExceptionOfType(InvalidUserRequestException.class)
                .isThrownBy(() -> registrationService.registerUser(secondRegistrationRequest));
    }

    @Test
    @Transactional
    void shouldNotRegisterUserWhenInvalidRequest() {
        //given
        String username = "username" + userSpecific.incrementAndGet();
        RegistrationRequest registrationRequest = getInvalidRegistrationRequest(username, username);

        //when then

        assertThatExceptionOfType(InvalidUserRequestException.class)
                .isThrownBy(() -> registrationService.registerUser(registrationRequest));
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
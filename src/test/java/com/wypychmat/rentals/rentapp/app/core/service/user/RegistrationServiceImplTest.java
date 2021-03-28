package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.TestContainerBase;
import com.wypychmat.rentals.rentapp.app.core.controller.dto.request.RegistrationRequest;
import com.wypychmat.rentals.rentapp.app.core.controller.dto.response.UserDto;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidUserRequestException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

// TODO: 28.03.2021 check received message
class RegistrationServiceImplTest extends TestContainerBase {
    private static RegistrationService<MimeMessage> registrationService;
    private static RegisterUserDao registerUserDao;
    private static AtomicInteger userSpecific;

    @Container
    static GenericContainer greenMailContainer = new GenericContainer<>(DockerImageName.parse("greenmail/standalone:1.6.2"))
            .withEnv("GREENMAIL_OPTS", "-Dgreenmail.setup.test.all -Dgreenmail.hostname=0.0.0.0 -Dgreenmail.users=test:test")
            .withExposedPorts(3025);

    @DynamicPropertySource
    static void configureMailHost(DynamicPropertyRegistry registry) {
        registry.add("spring.mail.host", greenMailContainer::getHost);
        registry.add("spring.mail.port", greenMailContainer::getFirstMappedPort);
    }


    @BeforeAll
    static void setUp(@Autowired RegistrationService<MimeMessage> registrationService,
                      @Autowired RegisterUserDao registerUserDao) {
        RegistrationServiceImplTest.registerUserDao = registerUserDao;
        RegistrationServiceImplTest.registrationService = registrationService;
        userSpecific = new AtomicInteger(0);
    }


    @Test
    void shouldRegisterUserWhenNew() {
        //given
        String username = "username" + userSpecific.incrementAndGet();
        RegistrationRequest registrationRequest = getValidRegistrationRequest(username, username);
        //when
        registerUserDao.deleteUserByUsername(username);
        Optional<UserDto> user = registrationService.registerUser(registrationRequest);
        //then
        assertThat(user).isPresent();
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

}
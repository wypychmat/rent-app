package com.wypychmat.rentals.rentapp.app.core.security;

import com.wypychmat.rentals.rentapp.app.core.TestContainerBaseWithEmail;
import com.wypychmat.rentals.rentapp.app.core.model.user.Role;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import com.wypychmat.rentals.rentapp.app.core.model.user.constant.ApplicationMainRole;
import com.wypychmat.rentals.rentapp.app.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Testcontainers
class CreationAdminAndModeratorTest extends TestContainerBaseWithEmail {


    @Test
    @Transactional
    void shouldFindAdminWithSpecificAuthority(@Autowired UserRepository userRepository,
                                              @Autowired AdminAndModeratorProvider adminAndModeratorProvider) {
        //given
        AdminOrModerator adminCredentials = adminAndModeratorProvider.prepareAdminCredentials();

        //when
        Optional<User> optionalAdmin = userRepository.findByUsername(adminCredentials.getName());

        //then
        assertThat(optionalAdmin).isPresent();
        User admin = optionalAdmin.get();
        Optional<Role> adminRole = admin.getUserRoles().stream()
                .filter(role -> role.getRoleName().equals(ApplicationMainRole.ADMIN.name())).findFirst();
        assertThat(adminRole).isPresent();
    }

    @Test
    @Transactional
    void shouldFindModeratorWithSpecificAuthority(@Autowired UserRepository userRepository,
                                              @Autowired AdminAndModeratorProvider adminAndModeratorProvider) {
        //given
        AdminOrModerator moderatorCredentials = adminAndModeratorProvider.prepareModeratorCredentials();

        //when
        Optional<User> optionalModerator = userRepository.findByUsername(moderatorCredentials.getName());

        //then
        assertThat(optionalModerator).isPresent();
        User admin = optionalModerator.get();
        Optional<Role> moderatorRole = admin.getUserRoles().stream()
                .filter(role -> role.getRoleName().equals(ApplicationMainRole.MODERATOR.name())).findFirst();
        assertThat(moderatorRole).isPresent();
    }

}
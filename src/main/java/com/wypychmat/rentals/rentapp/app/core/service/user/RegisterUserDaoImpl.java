package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.exception.InvalidUserRequestException;
import com.wypychmat.rentals.rentapp.app.core.model.projection.UsernameEmail;
import com.wypychmat.rentals.rentapp.app.core.model.user.RegisterToken;
import com.wypychmat.rentals.rentapp.app.core.model.user.Role;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import com.wypychmat.rentals.rentapp.app.core.model.user.ApplicationMainRole;
import com.wypychmat.rentals.rentapp.app.core.repository.RegisterTokenRepository;
import com.wypychmat.rentals.rentapp.app.core.repository.RoleRepository;
import com.wypychmat.rentals.rentapp.app.core.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
class RegisterUserDaoImpl implements RegisterUserDao {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegisterTokenRepository registerTokenRepository;

    RegisterUserDaoImpl(UserRepository userRepository,
                        RoleRepository roleRepository,
                        PasswordEncoder passwordEncoder, RegisterTokenRepository registerTokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.registerTokenRepository = registerTokenRepository;
    }


    @Override
    public Optional<UsernameEmail> existByUsernameAndEmail(String username,
                                                           String email) throws InvalidUserRequestException {
        return userRepository.existByUsernameOrEmail(username, email);
    }

    @Override
    @Transactional
    public Optional<User> saveUser(User user) {
        try {
            Optional<Role> userRole = roleRepository.findByRoleName(ApplicationMainRole.USER.name());
            if (userRole.isPresent()) {
                HashSet<Role> roles = new HashSet<>();
                Role role = userRole.get();
                roles.add(role);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setUserRoles(roles);
                User save = userRepository.save(user);
                return Optional.of(save);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<RegisterToken> saveToken(RegisterToken registerToken) {
        try {
            RegisterToken save = registerTokenRepository.save(registerToken);
            return Optional.of(save);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<RegisterToken> findToken(String token) {
        return registerTokenRepository.findByToken(token);
    }

    @Override
    public void deleteUserByUsername(String username) {
        userRepository.deleteUserByUsername(username);
    }

    @Override
    public void enableUserById(Long id) {
        userRepository.enableUserById(id);
    }

    @Override
    public void deleteTokenExpectGiven(RegisterToken registerToken, long userId) {
        CompletableFuture.runAsync(() -> registerTokenRepository.deleteTokenExpectGiven(registerToken.getId(), userId));
    }
}

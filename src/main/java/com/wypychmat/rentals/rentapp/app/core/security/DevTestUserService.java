package com.wypychmat.rentals.rentapp.app.core.security;

import com.wypychmat.rentals.rentapp.app.core.repository.UserRepository;
import com.wypychmat.rentals.rentapp.app.core.model.user.Role;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Profile({"dev", "test","drop"})
class DevTestUserService implements UserService {

    private final UserRepository userRepository;

    @Autowired
    DevTestUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Optional<UserDetailsModel> getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Set<SimpleGrantedAuthority> authorities = grantedAuthorityMapper(user.getUserRoles());
            UserDetailsModel userDetails = new UserDetailsModel(user.getId(), user.getUsername(),
                    user.getPassword(),
                    authorities,
                    user.isEnabled());
            return Optional.of(userDetails);
        }
        return Optional.empty();
    }


    private Set<SimpleGrantedAuthority> grantedAuthorityMapper(Set<Role> roles) {
        Set<SimpleGrantedAuthority> authorities =
                roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                        .collect(Collectors.toSet());
        roles.forEach(role -> authorities.addAll(role.getRolePermissions().stream().map(rolePermission ->
                new SimpleGrantedAuthority(rolePermission.getPermissionName().name())
        ).collect(Collectors.toSet())));
        return authorities;
    }
}

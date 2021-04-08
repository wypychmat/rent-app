package com.wypychmat.rentals.rentapp.app.core.security;

import com.wypychmat.rentals.rentapp.app.core.repository.RoleRepository;
import com.wypychmat.rentals.rentapp.app.core.repository.UserRepository;
import com.wypychmat.rentals.rentapp.app.core.model.user.Role;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import com.wypychmat.rentals.rentapp.app.core.model.user.ApplicationMainRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
 class CreateAdminAndModeratorService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    CreateAdminAndModeratorService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void saveAdminOrModIfNotExist(User user,ApplicationMainRole appRole){
        if(!userRepository.existByUsername(user.getUsername())) {
            Optional<Role> byRoleName = roleRepository.findByRoleName(appRole.name());
            if(byRoleName.isPresent()) {
                Role role = byRoleName.get();
                user.addRoles(role);
                userRepository.save(user);
            }
        }
    }
}

package com.wypychmat.rentals.rentapp.app.core.security;

import com.wypychmat.rentals.rentapp.app.core.repository.RoleRepository;
import com.wypychmat.rentals.rentapp.app.core.repository.UserRepository;
import com.wypychmat.rentals.rentapp.app.core.user.Role;
import com.wypychmat.rentals.rentapp.app.core.user.User;
import com.wypychmat.rentals.rentapp.app.core.user.constant.ApplicationMainRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
 class CreateAdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    CreateAdminService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void saveAdminIfNotExist(User user){
        if(!userRepository.existByUsername(user.getUsername())) {
            Optional<Role> byRoleName = roleRepository.findByRoleName(ApplicationMainRole.ADMIN.name());
            if(byRoleName.isPresent()) {
                Role role = byRoleName.get();
                user.addRoles(role);
                userRepository.save(user);
            }
        }
    }
}

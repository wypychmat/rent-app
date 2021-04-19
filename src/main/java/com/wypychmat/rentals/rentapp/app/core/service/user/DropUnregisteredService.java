package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import com.wypychmat.rentals.rentapp.app.core.repository.RegisterTokenRepository;
import com.wypychmat.rentals.rentapp.app.core.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
class DropUnregisteredService {

    private final RegisterTokenRepository registerTokenRepository;
    private final UserRepository userRepository;


    DropUnregisteredService(RegisterTokenRepository registerTokenRepository, UserRepository userRepository) {
        this.registerTokenRepository = registerTokenRepository;
        this.userRepository = userRepository;
    }

    void drop() {
        List<User> users = registerTokenRepository.getNotFullyRegisteredUser(new Date().getTime());
        deleteNotFullyRegistered(users);
    }

    @Transactional
    void deleteNotFullyRegistered(List<User> userIds) {
        if (userIds.size() > 0) {
            userRepository.deleteAll(userIds);
        }
    }
}

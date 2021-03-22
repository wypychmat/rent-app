package com.wypychmat.rentals.rentapp.app.core.repository;

import com.wypychmat.rentals.rentapp.app.core.model.projection.UsernameEmail;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query(name = "User.existByUsername")
    boolean existByUsername(@Param("username") String username);

    @Query(name = "User.existByUsernameOrEmail")
    Optional<UsernameEmail> existByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

     void deleteUserByUsername(String username);


}

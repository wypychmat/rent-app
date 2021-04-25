package com.wypychmat.rentals.rentapp.app.core.repository;

import com.wypychmat.rentals.rentapp.app.core.model.projection.UserWithRoles;
import com.wypychmat.rentals.rentapp.app.core.model.projection.UsernameEmail;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query(name = "User.existByUsername")
    boolean existByUsername(@Param("username") String username);

    @Query(name = "User.existByUsernameOrEmail")
    Optional<UsernameEmail> existByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    @Transactional
    @Modifying
    void deleteUserByUsername(String username);

    @Transactional
    @Modifying
    @Query(name = "User.enableUserById")
    void enableUserById(@Param("id") Long id);

    Optional<User> getUserByUsernameAndEmail(String username, String email);


    @Query(value = "SELECT user.id as id, username as username," +
            " (SELECT GROUP_CONCAT(CONCAT(role.role_name)) FROM" +
            " role JOIN user_roles as ur ON (user.id = ur.user_id) WHERE role.id = ur.role_id) as roles," +
            " email as email, is_enabled as enabled FROM user " +
            "WHERE username LIKE :username and email LIKE :email AND is_enabled = :enabled",
            nativeQuery = true, countQuery = "SELECT count(user.id) from user")
    Page<UserWithRoles> getUserWithRoles(Pageable pageable, String username, String email, boolean enabled);


}

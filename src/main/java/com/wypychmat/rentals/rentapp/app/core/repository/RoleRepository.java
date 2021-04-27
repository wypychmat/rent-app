package com.wypychmat.rentals.rentapp.app.core.repository;

import com.wypychmat.rentals.rentapp.app.core.model.projection.RoleProjection;
import com.wypychmat.rentals.rentapp.app.core.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);

    @Query("SELECT r.roleName as roleName FROM Role r JOIN User u WHERE u.id = :userId")
    Set<RoleProjection> findRolesByUserIdWithProjection(Long userId);
}

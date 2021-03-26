package com.wypychmat.rentals.rentapp.app.core.repository;

import com.wypychmat.rentals.rentapp.app.core.model.user.RegisterToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterTokenRepository extends JpaRepository<RegisterToken, Long> {
}

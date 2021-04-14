package com.wypychmat.rentals.rentapp.app.core.repository;

import com.wypychmat.rentals.rentapp.app.core.model.user.RegisterToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RegisterTokenRepository extends JpaRepository<RegisterToken, Long> {

    Optional<RegisterToken> findByToken(String token);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM RegisterToken t WHERE t.id <> :tokenId AND t.user.id = :userId")
    void deleteTokenExpectGiven(long tokenId, long userId);
}

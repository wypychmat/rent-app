package com.wypychmat.rentals.rentapp.app.core.repository;

import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.BookIdProjection;
import com.wypychmat.rentals.rentapp.app.core.model.rent.BookHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookHistory, Long> {

    @Query(value = "SELECT b.id as bookId, b.user_id as userId, b.vehicle_id as vehicleId " +
            "FROM book_history as b LIMIT 1",
            nativeQuery = true)
    Optional<BookIdProjection> getBookProjectionWithId(Long bookId);

}

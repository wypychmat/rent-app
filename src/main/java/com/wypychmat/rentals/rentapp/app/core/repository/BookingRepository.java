package com.wypychmat.rentals.rentapp.app.core.repository;

import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.BookIdProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ExtendedBookingProjection;
import com.wypychmat.rentals.rentapp.app.core.model.rent.Bookings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Bookings, Long> {

    @Query(value = "SELECT b.id as bookId, b.user_id as userId, b.vehicle_id as vehicleId " +
            "FROM book_history as b LIMIT 1",
            nativeQuery = true)
    Optional<BookIdProjection> getBookProjectionWithId(Long bookId);

    @Query(value = "SELECT b.id as bookId, u.id as userId, v.id as vehicleId, b.bookDate as bookDate, b.startRentDate as startRentDate," +
            " b.endRentDate as endRentDate, b.expiresDate as expiresDate, mf.manufacturer as manufacturer, " +
            "m.model as model, v.registrationPlate as registrationPlate FROM Bookings b" +
            " LEFT JOIN b.user u ON u.id = b.user.id LEFT JOIN b.vehicle v ON v.id = b.vehicle.id LEFT join v.model m " +
            "ON v.model.id = m.id LEFT JOIN m.manufacturer mf ON m.manufacturer.id = mf.id")
    Page<ExtendedBookingProjection> getBookings(Pageable pageable);

}

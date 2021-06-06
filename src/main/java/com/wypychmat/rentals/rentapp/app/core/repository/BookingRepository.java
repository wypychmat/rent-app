package com.wypychmat.rentals.rentapp.app.core.repository;

import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.BookingIdProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ExtendedBookingProjection;
import com.wypychmat.rentals.rentapp.app.core.model.rent.Bookings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Bookings, Long> {

    @Query(value = "SELECT b.id AS bookId, b.user_id AS userId, b.vehicle_id AS vehicleId " +
            "FROM bookings AS b LIMIT 1",
            nativeQuery = true)
    Optional<BookingIdProjection> getBookProjectionWithId(Long bookId);

    @Query(name = "Bookings.getAllBookings")
    Page<ExtendedBookingProjection> getBookings(Pageable pageable);

    @Query(name = "Bookings.getByBookIdAndUserId")
    Optional<ExtendedBookingProjection> findByIdAndUserIdWithProjection(Long bookId, Long userId);

    @Query(value = "SELECT b.vehicle.id FROM Bookings b WHERE b.id =:bookingId AND b.user.id=:userId ")
    Optional<Long> getVehicleIdFromBooking(Long bookingId, Long userId);


}

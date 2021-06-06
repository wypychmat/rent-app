package com.wypychmat.rentals.rentapp.app.core.service.rent;

import com.wypychmat.rentals.rentapp.app.core.dto.rent.BookingRequest;
import com.wypychmat.rentals.rentapp.app.core.dto.rent.ConfirmBookingRequest;
import com.wypychmat.rentals.rentapp.app.core.dto.rent.ReturnedVehicle;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ManageRentProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.UserRentProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.BookingIdProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ExtendedBookingProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.function.BiPredicate;

interface RentService {
    void bookVehicle(AddUserIdAspect.AppUser appUser, BookingRequest bookingRequest);

    void cancelBooking(AddUserIdAspect.AppUser appUser,
                       Long cancellationId,
                       BiPredicate<AddUserIdAspect.AppUser, BookingIdProjection> checkPredicate);

    Page<ExtendedBookingProjection> getBookings(Pageable pageable);


    Optional<ExtendedBookingProjection> getBookById(AddUserIdAspect.AppUser appUser, Long bookId);

    Optional<ExtendedBookingProjection> getBookById(Long bookId, Long userId);

    void confirmRent(ConfirmBookingRequest confirmBookingRequest);

    Page<UserRentProjection> getRentHistories(AddUserIdAspect.AppUser appUser, Pageable pageable);

    Page<ManageRentProjection> getRentHistoriesAsManager(Pageable pageable, Long userId, String rentId);

    void confirmReturnVehicle(ReturnedVehicle returnedVehicle);

}

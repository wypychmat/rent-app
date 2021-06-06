package com.wypychmat.rentals.rentapp.app.core.service.rent;

import com.wypychmat.rentals.rentapp.app.core.dto.rent.BookingRequest;
import com.wypychmat.rentals.rentapp.app.core.dto.rent.ConfirmBookingRequest;
import com.wypychmat.rentals.rentapp.app.core.dto.rent.ReturnedVehicle;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ManageRentProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.UserRentProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ExtendedBookingProjection;
import com.wypychmat.rentals.rentapp.app.core.service.rent.AddUserIdAspect.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RentFacade {
    private static final boolean MANAGER_AUTHORITY = true;
    private final RentService rentService;

    public RentFacade(RentService rentService) {
        this.rentService = rentService;
    }

    public void bookVehicle(BookingRequest vehicleId) {
        rentService.bookVehicle(new AppUser(), vehicleId);
    }

    public void cancelBooking(Long cancellationId) {
        rentService.cancelBooking(new AppUser(), cancellationId, ((appUser, bookingIdProjection) ->
                appUser.getUserId().equals(bookingIdProjection.getUserId())));
    }

    public void cancelBookingByManager(Long cancellationId) {
        rentService.cancelBooking(new AppUser(), cancellationId, ((appUser, bookingIdProjection) -> MANAGER_AUTHORITY));
    }

    public Page<ExtendedBookingProjection> getBookings(Pageable pageable) {
        return rentService.getBookings(pageable);
    }

    public Optional<ExtendedBookingProjection> getBookingInfo(Long bookId, Long userId) {
        if (userId == null) {
            return rentService.getBookById(new AppUser(), bookId);
        }
        return rentService.getBookById(bookId, userId);
    }

    public void confirmRent(ConfirmBookingRequest confirmBookingRequest) {
        rentService.confirmRent(confirmBookingRequest);
    }

    public Page<UserRentProjection> getRentHistories(Pageable pageable) {
       return rentService.getRentHistories(new AppUser(), pageable);
    }

    public Page<ManageRentProjection> getRentHistoriesAsManager(Pageable pageable, Long userId, String rentId) {
        return rentService.getRentHistoriesAsManager(pageable,userId,rentId);
    }

    public void confirmReturnVehicle(ReturnedVehicle returnedVehicle) {
        rentService.confirmReturnVehicle(returnedVehicle);
    }
}

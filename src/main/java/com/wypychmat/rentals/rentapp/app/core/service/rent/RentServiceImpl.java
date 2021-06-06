package com.wypychmat.rentals.rentapp.app.core.service.rent;

import com.wypychmat.rentals.rentapp.app.core.dto.rent.BookingRequest;
import com.wypychmat.rentals.rentapp.app.core.dto.rent.ConfirmBookingRequest;
import com.wypychmat.rentals.rentapp.app.core.dto.rent.ReturnedVehicle;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ManageRentProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.UserRentProjection;
import com.wypychmat.rentals.rentapp.app.core.model.builder.RentHistoryBuilder;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.BookingIdProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ExtendedBookingProjection;
import com.wypychmat.rentals.rentapp.app.core.model.rent.Bookings;
import com.wypychmat.rentals.rentapp.app.core.model.rent.RentHistory;
import com.wypychmat.rentals.rentapp.app.core.model.rent.RentStatus;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Vehicle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.function.BiPredicate;

//todo wrap method in retryPattern decorator

@Service
class RentServiceImpl implements RentService {
    private final RentDbPerformer rentDbPerformer;
    private final Integer expiresAfterDay;

    RentServiceImpl(RentDbPerformer rentDbPerformer,
                    @Value("${rent.book.expiresAfter}") Integer expiresAfterDay) {
        this.rentDbPerformer = rentDbPerformer;
        this.expiresAfterDay = expiresAfterDay;
    }

    @Override
    public void bookVehicle(AddUserIdAspect.AppUser appUser, BookingRequest bookingRequest) {
        // todo add verification request
        checkVehicle(bookingRequest);
        rentDbPerformer.saveNewBook(prepareNewBook(appUser, bookingRequest));
    }

    private Bookings prepareNewBook(AddUserIdAspect.AppUser appUser, BookingRequest bookingRequest) {
        return new Bookings(new User(appUser.getUserId()),
                bookingRequest.getStartDate(), bookingRequest.getEndDate(), new Vehicle(bookingRequest.getVehicleId()),
                expiresAfterDay);
    }

    private void checkVehicle(BookingRequest bookingRequest) {
        // todo add specific exception with handler
        Vehicle vehicle = rentDbPerformer.findById(bookingRequest.getVehicleId()).orElseThrow();
        checkIsAvailAbleOrThrow(vehicle);
    }

    private void checkIsAvailAbleOrThrow(Vehicle vehicle) {
        if (!vehicle.getStatus().equals(RentStatus.AVAILABLE))
            // todo create exception handler
            throw new IllegalStateException("already not available");
    }

    @Override
    public void cancelBooking(AddUserIdAspect.AppUser appUser,
                              Long cancellationId,
                              BiPredicate<AddUserIdAspect.AppUser, BookingIdProjection> checkPredicate) {
        // todo return state
        Optional<BookingIdProjection> bookProjectionWithId = rentDbPerformer.getBookProjectionWithId(cancellationId);
        if (bookProjectionWithId.isPresent()) {
            BookingIdProjection book = bookProjectionWithId.get();
            if (checkPredicate.test(appUser, book)) {
                rentDbPerformer.cancelBookForVehicle(book);
            }
        }
    }

    @Override
    public Page<ExtendedBookingProjection> getBookings(Pageable pageable) {
        return rentDbPerformer.getBookings(pageable);
    }

    @Override
    public Optional<ExtendedBookingProjection> getBookById(AddUserIdAspect.AppUser appUser, Long bookId) {
        return rentDbPerformer.getBookByBookingAndUser(bookId, appUser.getUserId());
    }

    @Override
    public Optional<ExtendedBookingProjection> getBookById(Long bookId, Long userId) {
        return rentDbPerformer.getBookByBookingAndUser(bookId, userId);
    }

    @Override
    public void confirmRent(ConfirmBookingRequest confirmBookingRequest) {
        // todo add verification
        Optional<Long> vehicleId = rentDbPerformer.getVehicleIdFromBooking(confirmBookingRequest);
        if (vehicleId.isPresent()) {
            RentHistory rentHistory = buildRentHistory(confirmBookingRequest, vehicleId.get());
            rentDbPerformer.confirmRentForUser(confirmBookingRequest, rentHistory);
        }
        // todo already confirmed or invalid data
        // todo return status
    }

    @Override
    public Page<UserRentProjection> getRentHistories(AddUserIdAspect.AppUser appUser, Pageable pageable) {
        return rentDbPerformer.getRentHistories(pageable, appUser.getUserId());
    }

    @Override
    public Page<ManageRentProjection> getRentHistoriesAsManager(Pageable pageable, Long userId, String rentId) {
        return rentDbPerformer.getRentHistoriesByManager(pageable, userId, rentId);
    }

    @Override
    public void confirmReturnVehicle(ReturnedVehicle returnedVehicle) {
        boolean exist = rentDbPerformer.checkRentExist(returnedVehicle);
        if (exist) {
            rentDbPerformer.updateRentVehicle(returnedVehicle,new Date());
        }
        // todo return value
    }

    private RentHistory buildRentHistory(ConfirmBookingRequest confirmBookingRequest, Long vehicleId) {
        return RentHistoryBuilder.builder()
                .setOdometer(confirmBookingRequest.getOdometer())
                .setVehicleId(vehicleId)
                .setUserId(confirmBookingRequest.getUserId()).build();
    }
}





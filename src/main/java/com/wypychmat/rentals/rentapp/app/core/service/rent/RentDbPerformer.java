package com.wypychmat.rentals.rentapp.app.core.service.rent;


import com.wypychmat.rentals.rentapp.app.core.dto.rent.ConfirmBookingRequest;
import com.wypychmat.rentals.rentapp.app.core.dto.rent.ReturnedVehicle;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ManageRentProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.UserRentProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.BookingIdProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ExtendedBookingProjection;
import com.wypychmat.rentals.rentapp.app.core.model.rent.Bookings;
import com.wypychmat.rentals.rentapp.app.core.model.rent.CanceledBook;
import com.wypychmat.rentals.rentapp.app.core.model.rent.RentHistory;
import com.wypychmat.rentals.rentapp.app.core.model.rent.RentStatus;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Vehicle;
import com.wypychmat.rentals.rentapp.app.core.repository.BookingRepository;
import com.wypychmat.rentals.rentapp.app.core.repository.CanceledRepository;
import com.wypychmat.rentals.rentapp.app.core.repository.RentRepository;
import com.wypychmat.rentals.rentapp.app.core.repository.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;


@Service
class RentDbPerformer {

    private final BookingRepository bookingRepository;
    private final CanceledRepository canceledRepository;
    private final VehicleRepository vehicleRepository;
    private final RentRepository rentRepository;

    RentDbPerformer(BookingRepository bookingRepository,
                    CanceledRepository canceledRepository,
                    VehicleRepository vehicleRepository, RentRepository rentRepository) {
        this.bookingRepository = bookingRepository;
        this.canceledRepository = canceledRepository;
        this.vehicleRepository = vehicleRepository;
        this.rentRepository = rentRepository;
    }

    @Transactional
    public void cancelBookForVehicle(BookingIdProjection book) {
        bookingRepository.deleteById(book.getBookId());
        canceledRepository.save(getCanceled(book));
        vehicleRepository.changeStatus(RentStatus.AVAILABLE, book.getVehicleId());
    }

    private CanceledBook getCanceled(BookingIdProjection book) {
        return new CanceledBook(new User(book.getUserId()), new Vehicle(book.getVehicleId()));
    }

    @Transactional
    public void saveNewBook(Bookings book) {
        Long vehicleId = book.getVehicle().getId();
        bookingRepository.save(book);
        vehicleRepository.changeStatus(RentStatus.BOOKED, vehicleId);
    }

    public Optional<BookingIdProjection> getBookProjectionWithId(Long cancellationId) {
        return bookingRepository.getBookProjectionWithId(cancellationId);
    }

    public Optional<Vehicle> findById(Long vehicleId) {
        return vehicleRepository.findById(vehicleId);
    }

    public Page<ExtendedBookingProjection> getBookings(Pageable pageable) {
        return bookingRepository.getBookings(pageable);

    }

    public Optional<ExtendedBookingProjection> getBookByBookingAndUser(Long bookId, Long userId) {
        return bookingRepository.findByIdAndUserIdWithProjection(bookId, userId);
    }

    @Transactional
    public void confirmRentForUser(ConfirmBookingRequest confirmBookingRequest, RentHistory rentHistory) {
        bookingRepository.deleteById(confirmBookingRequest.getBookId());
        rentRepository.save(rentHistory);
    }

    public Optional<Long> getVehicleIdFromBooking(ConfirmBookingRequest confirmBookingRequest) {
        return bookingRepository.getVehicleIdFromBooking(confirmBookingRequest.getBookId(), confirmBookingRequest.getUserId());
    }

    public Page<UserRentProjection> getRentHistories(Pageable pageable, Long userId) {
        return rentRepository.getRentHistories(pageable, userId);
    }

    public Page<ManageRentProjection> getRentHistoriesByManager(Pageable pageable, Long userId, String rentId) {
        return rentRepository.getRentHistoriesAsManager(pageable, userId, rentId);
    }

    public boolean checkRentExist(ReturnedVehicle returnedVehicle) {
        return rentRepository.existsByIdAndUserIdAndVehicleId(
                returnedVehicle.getRentId(),
                returnedVehicle.getUserId(),
                returnedVehicle.getVehicleId());
    }

    @Transactional
    public void updateRentVehicle(ReturnedVehicle returnedVehicle, Date endDate) {
        rentRepository.updateOdometerAndRentDate(
                returnedVehicle.getRentId(),
                returnedVehicle.getRentId(),
                endDate);
        vehicleRepository.changeStatus(RentStatus.AVAILABLE, returnedVehicle.getVehicleId());
    }
}

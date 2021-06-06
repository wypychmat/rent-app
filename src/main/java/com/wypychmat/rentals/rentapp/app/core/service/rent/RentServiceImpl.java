package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.BookRequest;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.BookHistory;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.RentStatus;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Vehicle;
import com.wypychmat.rentals.rentapp.app.core.repository.BookRepository;
import com.wypychmat.rentals.rentapp.app.core.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
class RentServiceImpl implements RentService {
    private final VehicleRepository vehicleRepository;
    private final BookRepository bookRepository;
    private final Integer expiresAfterDay;

    RentServiceImpl(VehicleRepository vehicleRepository,
                    BookRepository bookRepository,
                    @Value("${rent.book.expiresAfter}") Integer expiresAfterDay) {
        this.vehicleRepository = vehicleRepository;
        this.bookRepository = bookRepository;
        this.expiresAfterDay = expiresAfterDay;
    }


    @Override
    @Transactional
    public void bookVehicle(AddUserIdAspect.AppUser appUser, BookRequest bookRequest) {
        // TODO:  add verification
        Vehicle vehicle = vehicleRepository.findById(bookRequest.getVehicleId()).orElseThrow();
        if (vehicle.getStatus().equals(RentStatus.AVAILABLE)) {
            BookHistory bookHistory = new BookHistory(new User(appUser.getUserId()),
                    bookRequest.getStartDate(),bookRequest.getEndDate(), vehicle,
                    expiresAfterDay);
            bookRepository.save(bookHistory);
        } else {
            // TODO: create exception handler
            throw new IllegalStateException("already not available");
        }
    }

    @Override
    public void cancelBook(AddUserIdAspect.AppUser appUser, String cancellationId) {

    }
}

package com.wypychmat.rentals.rentapp.app.core.service.rent;


import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.BookIdProjection;
import com.wypychmat.rentals.rentapp.app.core.model.rent.CanceledBook;
import com.wypychmat.rentals.rentapp.app.core.model.rent.RentStatus;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Vehicle;
import com.wypychmat.rentals.rentapp.app.core.repository.BookRepository;
import com.wypychmat.rentals.rentapp.app.core.repository.CanceledRepository;
import com.wypychmat.rentals.rentapp.app.core.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
class RentTransactionPerformer {

    private final BookRepository bookRepository;
    private final CanceledRepository canceledRepository;
    private final VehicleRepository vehicleRepository;

    RentTransactionPerformer(BookRepository bookRepository,
                             CanceledRepository canceledRepository,
                             VehicleRepository vehicleRepository) {
        this.bookRepository = bookRepository;
        this.canceledRepository = canceledRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    public void cancelBookForVehicle(BookIdProjection book) {
        bookRepository.deleteById(book.getBookId());
        canceledRepository.save(getCanceled(book));
        vehicleRepository.changeStatus(RentStatus.AVAILABLE, book.getVehicleId());
    }

    private CanceledBook getCanceled(BookIdProjection book) {
        return new CanceledBook(new User(book.getUserId()), new Vehicle(book.getVehicleId()));
    }
}

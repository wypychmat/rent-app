package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.BookRequest;
import com.wypychmat.rentals.rentapp.app.core.service.vehicle.AddUserIdAspect.AppUser;
import org.springframework.stereotype.Service;

@Service
public class RentFacade {

    private final RentService rentService;

    public RentFacade(RentService rentService) {
        this.rentService = rentService;
    }

    public void bookVehicle(BookRequest vehicleId) {
        rentService.bookVehicle(new AppUser(),vehicleId);
    }

    public void cancelBook(String cancellationId) {
        rentService.cancelBook(new AppUser(), cancellationId);
    }
}

package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.BookRequest;

interface RentService {
    void bookVehicle(AddUserIdAspect.AppUser appUser, BookRequest bookRequest);

    void cancelBook(AddUserIdAspect.AppUser appUser, String cancellationId);
}

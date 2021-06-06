package com.wypychmat.rentals.rentapp.app.core.model.projection.domain;

import com.wypychmat.rentals.rentapp.app.core.model.rent.RentStatus;

public interface BaseVehicleProjection {

     Long getId();

     String getRegistrationPlate();

     String getModel();

     RentStatus getRentStatus();
}

package com.wypychmat.rentals.rentapp.app.core.model.projection;

import com.wypychmat.rentals.rentapp.app.core.model.vehicle.RentStatus;

public interface BaseVehicleProjection {

     Long getId();

     String getRegistrationPlate();

     String getModel();

     RentStatus getRentStatus();
}

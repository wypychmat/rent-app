package com.wypychmat.rentals.rentapp.app.core.model.projection;

import com.wypychmat.rentals.rentapp.app.core.model.vehicle.RentStatus;

public interface VehicleProjection {
    Long getId();

    String getModel();

    String getProducer();

    Integer getProductionYear();

    String getRegistrationPlate();

    RentStatus getStatus();

    String getEngine();
}

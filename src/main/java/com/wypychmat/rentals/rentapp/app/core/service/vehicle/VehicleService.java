package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.VehicleDto;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.BaseVehicleProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.VehicleProjection;
import com.wypychmat.rentals.rentapp.app.core.model.rent.RentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.URI;
import java.util.Optional;

interface VehicleService extends UriBuildAble<Long> {

    Page<VehicleProjection> getVehicles(Pageable pageable, String modelId, RentStatus rentStatus);

    URI addVehicle(VehicleDto vehicleDto);

    Optional<BaseVehicleProjection> getVehicleBaseInformationByPlate(String registrationPlate);


}

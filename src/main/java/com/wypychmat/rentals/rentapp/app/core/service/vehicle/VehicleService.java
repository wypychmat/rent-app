package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.model.projection.BaseVehicleProjection;
import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.VehicleDto;
import com.wypychmat.rentals.rentapp.app.core.model.projection.VehicleProjection;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.RentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VehicleService {

    Page<VehicleProjection> getVehicles(Pageable pageable, String modelId, RentStatus rentStatus);

    void addVehicle(VehicleDto vehicleDto);

    Optional<BaseVehicleProjection> getVehicleBaseInformationByPlate(String registrationPlate);



}

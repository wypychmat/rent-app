package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.VehicleDto;
import com.wypychmat.rentals.rentapp.app.core.model.projection.BaseVehicleProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.VehicleProjection;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.RentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleFacade {

    private final VehicleService vehicleService;

    public VehicleFacade(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PreAuthorize("hasAuthority('read')")
    public Page<VehicleProjection> getVehiclesByStatus(Pageable pageable, String modelId, RentStatus rentStatus) {
        return vehicleService.getVehicles(pageable, modelId, rentStatus);
    }

    @PreAuthorize("hasAuthority('manage')")
    public Page<VehicleProjection> getAvailableVehicles(Pageable pageable, String modelId) {
        return vehicleService.getVehicles(pageable, modelId, RentStatus.AVAILABLE);
    }

    public void addVehicle(VehicleDto vehicleDto) {
        vehicleService.addVehicle(vehicleDto);
    }

    public Optional<BaseVehicleProjection> getVehicleByRegistrationPlate(String registrationPlate) {
        return vehicleService.getVehicleBaseInformationByPlate(registrationPlate);
    }
}

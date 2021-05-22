package com.wypychmat.rentals.rentapp.app.core.controller;


import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.VehicleDto;
import com.wypychmat.rentals.rentapp.app.core.model.projection.BaseVehicleProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.VehicleProjection;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.RentStatus;
import com.wypychmat.rentals.rentapp.app.core.repository.VehicleRepository;
import com.wypychmat.rentals.rentapp.app.core.service.vehicle.VehicleFacade;
import com.wypychmat.rentals.rentapp.app.core.util.ApiVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "${api.base}" + "${api.path.vehicles}", produces = {ApiVersion.JSON, ApiVersion.V1_JSON})
public class VehicleControllerV1 {
    private final VehicleFacade vehicleFacade;

    public VehicleControllerV1(VehicleFacade vehicleFacade) {
        this.vehicleFacade = vehicleFacade;
    }

    @GetMapping
    public Page<VehicleProjection> getAllAvailableVehiclesByModelId(
            Pageable pageable,
            @RequestParam(defaultValue = "") String modelId,
            @RequestParam(required = false) RentStatus status) {

        if (status == null) {
            return vehicleFacade.getAvailableVehicles(pageable, modelId);
        }
        return vehicleFacade.getVehiclesByStatus(pageable, modelId, status);
    }


    @PostMapping("")
    @PreAuthorize("hasAuthority('write')")
    public String addVehicle(@RequestBody VehicleDto vehicleDto) {
        vehicleFacade.addVehicle(vehicleDto);
        return "null";
    }

    @GetMapping("/{plate}")
    @PreAuthorize("hasAuthority('manage')")
    public Optional<BaseVehicleProjection> getAllAvailableVehiclesByModelId(@PathVariable String plate) {
        return vehicleFacade.getVehicleByRegistrationPlate(plate);
    }


}

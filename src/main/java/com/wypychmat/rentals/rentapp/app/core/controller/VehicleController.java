package com.wypychmat.rentals.rentapp.app.core.controller;


import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.BookRequest;
import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.VehicleDto;
import com.wypychmat.rentals.rentapp.app.core.model.projection.BaseVehicleProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.VehicleProjection;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.RentStatus;
import com.wypychmat.rentals.rentapp.app.core.repository.VehicleRepository;
import com.wypychmat.rentals.rentapp.app.core.service.vehicle.VehicleFacade;
import com.wypychmat.rentals.rentapp.app.core.util.ApiVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping(value = "${api.base}" + "${api.path.vehicles}", produces = {ApiVersion.JSON, ApiVersion.V1_JSON})
public class VehicleControllerV1 {
    private final VehicleFacade vehicleFacade;

    public VehicleControllerV1(VehicleFacade vehicleFacade) {
        this.vehicleFacade = vehicleFacade;
    }

    @GetMapping
    public Page<VehicleProjection> getAllVehicleByModelId(
            Pageable pageable,
            @RequestParam(defaultValue = "") String modelId,
            @RequestParam(required = false) RentStatus status) {

        if (status == null) {
            return vehicleFacade.getAvailableVehicles(pageable, modelId);
        }
        return vehicleFacade.getVehiclesByStatus(pageable, modelId, status);
    }

    @GetMapping("/book")
//    @PreAuthorize("hasAuthority('rent')")
    public void bookVehicle(@RequestBody BookRequest bookRequest) {
        // todo add return value
        vehicleFacade.bookVehicle(bookRequest);
    }


    @PostMapping("")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<URI> addVehicle(@RequestBody VehicleDto vehicleDto) {
        return ResponseEntity.created(vehicleFacade.addVehicle(vehicleDto)).build();
    }

    @GetMapping("/{plate}")
    @PreAuthorize("hasAuthority('manage')")
    public Optional<BaseVehicleProjection> getAllVehicleByModelId(@PathVariable String plate) {
        return vehicleFacade.getVehicleByRegistrationPlate(plate);
    }

    @GetMapping("/confirm}")
    @PreAuthorize("hasAuthority('manage')")
    public void confirmRental(@RequestBody BaseVehicleProjection vehicleProjection) {
//        vehicleFacade.confirmBookRental();
    }


}

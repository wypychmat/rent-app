package com.wypychmat.rentals.rentapp.app.core.controller;


import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ManufacturerDto;
import com.wypychmat.rentals.rentapp.app.core.service.vehicle.VehiclesFacade;
import com.wypychmat.rentals.rentapp.app.core.util.ApiVersion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "${api.base}" + "${api.path.producer}", produces = {ApiVersion.JSON, ApiVersion.V1_JSON})
public class ManufacturerControllerV1 {
    private final VehiclesFacade vehiclesFacade;

    public ManufacturerControllerV1(VehiclesFacade vehiclesFacade) {
        this.vehiclesFacade = vehiclesFacade;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<?> addNew(@RequestBody ManufacturerDto manufacturerDto) {
       return vehiclesFacade.addManufacture(manufacturerDto)
                .map(x-> ResponseEntity.created(x).build())
                .orElseGet(()-> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}

package com.wypychmat.rentals.rentapp.app.core.controller;


import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ModelDto;
import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ModelPropertyDto;
import com.wypychmat.rentals.rentapp.app.core.service.vehicle.VehiclesFacade;
import com.wypychmat.rentals.rentapp.app.core.util.ApiVersion;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.function.Supplier;

@Controller
@RequestMapping(value = "${api.base}" + "${api.path.models}", produces = {ApiVersion.JSON, ApiVersion.V1_JSON})
public class ModelControllerV1 {

    private final VehiclesFacade vehiclesFacade;

    public ModelControllerV1(VehiclesFacade vehiclesFacade) {
        this.vehiclesFacade = vehiclesFacade;
    }

    @GetMapping("/all")
    ResponseEntity<?> getAllModel(Pageable pageable) {
        vehiclesFacade.getAllUniqueModel(pageable);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    ResponseEntity<?> addModel(@RequestBody ModelDto modelDto) {
        return vehiclesFacade.addModel(modelDto).map(value -> ResponseEntity.created(value).build())
                .orElseGet(getConflictResponse());
    }

    @PostMapping("/properties")
    @PreAuthorize("hasAuthority('write')")
    ResponseEntity<?> addModelProperty(@RequestBody ModelPropertyDto propertyDto, @RequestParam String property) {
        return ResponseEntity.created(vehiclesFacade.addProperty(propertyDto,property)).build();
    }

    private Supplier<ResponseEntity<Object>> getConflictResponse() {
        return () -> ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}

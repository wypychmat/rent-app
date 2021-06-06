package com.wypychmat.rentals.rentapp.app.core.controller;


import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ManufacturerDto;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ManufacturerProjection;
import com.wypychmat.rentals.rentapp.app.core.service.vehicle.ManufacturerFacade;
import com.wypychmat.rentals.rentapp.app.core.util.ApiVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${api.base}" + "${api.path.producer}", produces = {ApiVersion.JSON, ApiVersion.V1_JSON})
public class ManufacturerController {
    private final ManufacturerFacade manufacturerFacade;

    public ManufacturerController(ManufacturerFacade manufacturerFacade) {
        this.manufacturerFacade = manufacturerFacade;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<?> addNew(@RequestBody ManufacturerDto manufacturerDto) {
        return manufacturerFacade.addManufacture(manufacturerDto)
                .map(x -> ResponseEntity.created(x).build())
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('read')")
    public Page<ManufacturerProjection> getAllManufacturer(Pageable pageable, @RequestParam(defaultValue = "")
            String producer) {
        return manufacturerFacade.getAllManufacturer(pageable, producer);
    }




}

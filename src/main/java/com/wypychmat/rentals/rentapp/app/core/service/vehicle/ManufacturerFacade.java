package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.BaseManufacturerDto;
import com.wypychmat.rentals.rentapp.app.core.model.projection.ManufacturerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
public class ManufacturerFacade {
    private final ManufacturerService manufacturerService;

    public ManufacturerFacade(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    public Optional<URI> addManufacture(BaseManufacturerDto baseManufacturerDto) {
        return manufacturerService.addManufacturer(baseManufacturerDto);
    }

    public Page<ManufacturerProjection> getAllManufacturer(Pageable pageable, String producer) {
        return manufacturerService.getAllManufacturer(pageable,producer);
    }
}

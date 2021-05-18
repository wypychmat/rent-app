package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ManufacturerDto;
import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ModelDto;
import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ModelPropertyDto;
import com.wypychmat.rentals.rentapp.app.core.model.projection.ProjectionModel;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class VehiclesFacade {
    private final ModelService modelService;
    private final ManufacturerService manufacturerService;

    public VehiclesFacade(ModelService modelService, ManufacturerService manufacturerService) {
        this.modelService = modelService;
        this.manufacturerService = manufacturerService;
    }

   public Page<ProjectionModel>  getAllModel(Pageable pageable) {
        return modelService.getAllModel(pageable);
    }

    public Optional<URI> addModel(ModelDto modelDto) {
        return modelService.addModel(modelDto);
    }

    public Optional<URI> addManufacture(ManufacturerDto manufacturerDto) {
        return manufacturerService.addManufacturer(manufacturerDto);
    }

    public URI addProperty(ModelPropertyDto modelProperty, String propertyParam) {
        return modelService.addProperty(modelProperty,propertyParam);
    }

}

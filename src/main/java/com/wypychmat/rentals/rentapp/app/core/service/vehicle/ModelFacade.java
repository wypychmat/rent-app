package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ModelDto;
import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ModelPropertyDto;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ProjectionModel;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ProjectionModelProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
public class ModelFacade {
    private final ModelService modelService;

    public ModelFacade(ModelService modelService) {
        this.modelService = modelService;
    }

    public Page<ProjectionModel> getAllModel(Pageable pageable, String model) {
        return modelService.getAllModel(pageable, model);
    }

    public Optional<URI> addModel(ModelDto modelDto) {
        return modelService.addModel(modelDto);
    }


    public URI addProperty(ModelPropertyDto modelProperty, String propertyParam) {
        return modelService.addProperty(modelProperty, propertyParam);
    }

    public Page<ProjectionModel> getAllModelsByProducer(Pageable pageable, Long id) {
        return modelService.getAllModelsByProducer(pageable,id);
    }

    public Optional<ProjectionModel> getModelById(Long id) {
        return modelService.getModelById(id);
    }

    public Page<ProjectionModelProperty> getAllModelProperty(Pageable pageable, String property, String propertyValue) {
        return modelService.getAllPropertyModel(pageable,property,propertyValue);
    }

    public ProjectionModelProperty getModelPropertyById(Long id, String property) {
        return modelService.getModelPropertyById(id,property);
    }
}

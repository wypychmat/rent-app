package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ModelDto;
import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ModelPropertyDto;
import com.wypychmat.rentals.rentapp.app.core.model.projection.ProjectionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.URI;
import java.util.Optional;

interface ModelService extends UriBuildAble<Long> {
    Page<ProjectionModel> getAllModel(Pageable pageable, String model);

    Optional<URI> addModel(ModelDto modelDto);

    URI addProperty(ModelPropertyDto modelPropertyDto, String property);

}




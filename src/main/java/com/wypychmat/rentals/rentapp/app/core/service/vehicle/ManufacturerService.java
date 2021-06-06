package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ManufacturerDto;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ManufacturerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.URI;
import java.util.Optional;

interface ManufacturerService extends UriBuildAble<Long> {

    Optional<URI> addManufacturer(ManufacturerDto manufacturerDto);

    Page<ManufacturerProjection> getAllManufacturer(Pageable pageable, String manufacturer);
}

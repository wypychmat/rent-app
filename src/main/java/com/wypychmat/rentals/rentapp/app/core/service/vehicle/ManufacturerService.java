package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.BaseManufacturerDto;
import com.wypychmat.rentals.rentapp.app.core.model.projection.ManufacturerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.URI;
import java.util.Optional;

interface ManufacturerService extends UriBuildAble<Long> {

    Optional<URI> addManufacturer(BaseManufacturerDto baseManufacturerDto);

    Page<ManufacturerProjection> getAllManufacturer(Pageable pageable, String manufacturer);
}

package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ManufacturerDto;
import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ModelPropertyDto;

import java.net.URI;
import java.util.Optional;

interface ManufacturerService extends UriBuildAble<Long> {

    Optional<URI> addManufacturer(ManufacturerDto manufacturerDto);
}

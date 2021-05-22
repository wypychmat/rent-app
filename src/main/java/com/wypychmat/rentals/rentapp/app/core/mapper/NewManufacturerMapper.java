package com.wypychmat.rentals.rentapp.app.core.mapper;


import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.BaseManufacturerDto;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface NewManufacturerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "models", ignore = true)
    Manufacturer mapManufacturer(BaseManufacturerDto baseManufacturerDto);
}

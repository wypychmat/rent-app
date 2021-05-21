package com.wypychmat.rentals.rentapp.app.core.mapper;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.VehicleDto;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Engine;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Model;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface NewVehicleMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(source = "model", target = "model")
    @Mapping(source = "engine", target = "engine")
    @Mapping(source = "vehicleDto.productionYear", target = "productionYear")
    @Mapping(source = "vehicleDto.registrationPlate", target = "registrationPlate")
    @Mapping(target = "status", constant = "AVAILABLE")
    Vehicle toVehicle(VehicleDto vehicleDto, Model model, Engine engine);
}

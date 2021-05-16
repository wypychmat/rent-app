package com.wypychmat.rentals.rentapp.app.core.mapper;


import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ModelDto;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface NewModelMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "modelDto.model", target = "model")
    @Mapping(source = "modelDto.startProductionYear", target = "startProductionYear")
    @Mapping(source = "engines", target = "engines")
    @Mapping(source = "manufacturer", target = "manufacturer")
    @Mapping(source = "segment", target = "segment")
    @Mapping(source = "type", target = "type")
    Model mapModel(ModelDto modelDto, List<Engine> engines, Manufacturer manufacturer, Segment segment, Type type);
}

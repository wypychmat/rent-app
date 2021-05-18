package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ManufacturerDto;
import com.wypychmat.rentals.rentapp.app.core.mapper.NewManufacturerMapper;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Manufacturer;
import com.wypychmat.rentals.rentapp.app.core.repository.ManufacturerRepository;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private final NewManufacturerMapper manufacturerMapper;
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(NewManufacturerMapper manufacturerMapper,
                                   ManufacturerRepository manufacturerRepository) {
        this.manufacturerMapper = manufacturerMapper;
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public Optional<URI> addManufacturer(ManufacturerDto manufacturerDto) {
        Manufacturer save = manufacturerRepository.save(manufacturerMapper.mapManufacturer(manufacturerDto));
        return Optional.of(getUri(save::getId, "/{id}"));
    }


}

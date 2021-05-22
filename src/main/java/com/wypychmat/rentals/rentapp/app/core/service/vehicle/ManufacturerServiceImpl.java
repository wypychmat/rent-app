package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.BaseManufacturerDto;
import com.wypychmat.rentals.rentapp.app.core.mapper.NewManufacturerMapper;
import com.wypychmat.rentals.rentapp.app.core.model.projection.ManufacturerProjection;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Manufacturer;
import com.wypychmat.rentals.rentapp.app.core.repository.ManufacturerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
class ManufacturerServiceImpl implements ManufacturerService {

    private final NewManufacturerMapper manufacturerMapper;
    private final ManufacturerRepository manufacturerRepository;

    ManufacturerServiceImpl(NewManufacturerMapper manufacturerMapper,
                            ManufacturerRepository manufacturerRepository) {
        this.manufacturerMapper = manufacturerMapper;
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public Optional<URI> addManufacturer(BaseManufacturerDto baseManufacturerDto) {
        Manufacturer save = manufacturerRepository.save(manufacturerMapper.mapManufacturer(baseManufacturerDto));
        return Optional.of(getUri(save::getId, "/{id}"));
    }

    @Override
    public Page<ManufacturerProjection> getAllManufacturer(Pageable pageable, String manufacturer) {
       return manufacturerRepository.findAllManufacturer(pageable, manufacturer.toUpperCase());
    }


}

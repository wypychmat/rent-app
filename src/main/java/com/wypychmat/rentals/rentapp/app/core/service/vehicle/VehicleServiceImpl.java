package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ModelDto;
import com.wypychmat.rentals.rentapp.app.core.exception.vehicle.NoSuchModelMembersException;
import com.wypychmat.rentals.rentapp.app.core.mapper.NewModelMapper;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.*;
import com.wypychmat.rentals.rentapp.app.core.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
class VehicleServiceImpl implements VehicleService {

    private final ModelRepository modelRepository;
    private final SegmentRepository segmentRepository;
    private final EngineRepository engineRepository;
    private final TypeRepository typeRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final NewModelMapper newModelMapper;

    VehicleServiceImpl(ModelRepository modelRepository,
                       ManufacturerRepository manufacturerRepository,
                       SegmentRepository segmentRepository,
                       EngineRepository engineRepository,
                       TypeRepository typeRepository,
                       VehicleRepository vehicleRepository, NewModelMapper newModelMapper) {
        this.modelRepository = modelRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.segmentRepository = segmentRepository;
        this.engineRepository = engineRepository;
        this.typeRepository = typeRepository;
        this.newModelMapper = newModelMapper;
    }

    @Override
    public Page<Model> getAllUniqueModel(Pageable pageable) {
        String a = "";
        return modelRepository.findAll(pageable);
    }


    @Override
    @Transactional
    public void addModel(ModelDto modelDto) {
        // TODO: 16.05.2021 verify model
        List<Engine> engines = engineRepository.findAllById(modelDto.getEnginesId());
        checkEngineIsNotEmpty(engines);
        Manufacturer manufacturer = manufacturerRepository.findById(modelDto.getManufacturerId())
                .orElseThrow(NoSuchModelMembersException::new);
        Segment segment = segmentRepository.findById(modelDto.getSegmentId())
                .orElseThrow(NoSuchModelMembersException::new);
        Type type = typeRepository.findById(modelDto.getTypeId())
                .orElseThrow(NoSuchModelMembersException::new);
        Model model = newModelMapper.mapModel(modelDto, engines, manufacturer, segment, type);
        modelRepository.save(model);
    }

    private void checkEngineIsNotEmpty(List<Engine> engines) {
        if (engines.isEmpty())
            throw new NoSuchModelMembersException();
    }
}

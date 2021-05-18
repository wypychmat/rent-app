package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ModelDto;
import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ModelPropertyDto;
import com.wypychmat.rentals.rentapp.app.core.exception.vehicle.NoSuchModelMembersException;
import com.wypychmat.rentals.rentapp.app.core.mapper.NewModelMapper;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.*;
import com.wypychmat.rentals.rentapp.app.core.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
class ModelServiceImpl implements ModelService {

    public static final String DEFAULT_VARIABLE_PATH = "/{id}";
    private final ModelRepository modelRepository;
    private final SegmentRepository segmentRepository;
    private final EngineRepository engineRepository;
    private final TypeRepository typeRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final NewModelMapper newModelMapper;

    ModelServiceImpl(ModelRepository modelRepository,
                     ManufacturerRepository manufacturerRepository,
                     SegmentRepository segmentRepository,
                     EngineRepository engineRepository,
                     TypeRepository typeRepository,
                     NewModelMapper newModelMapper) {
        this.modelRepository = modelRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.segmentRepository = segmentRepository;
        this.engineRepository = engineRepository;
        this.typeRepository = typeRepository;
        this.newModelMapper = newModelMapper;
    }

    @PostConstruct
    void initializeEnums() {
        ModelProperty.engine.setSave((modelPropertyDto ->
                engineRepository.save(new Engine(modelPropertyDto.getName())).getId()));
        ModelProperty.type.setSave(
                (modelPropertyDto -> typeRepository.save(new Type(modelPropertyDto.getName())).getId()));
        ModelProperty.segment.setSave(
                (modelPropertyDto -> segmentRepository.save(new Segment(modelPropertyDto.getName())).getId()));
    }

    @Override
    public Page<Model> getAllUniqueModel(Pageable pageable) {
        return modelRepository.findAll(pageable);
    }


    @Override
    @Transactional
    public Optional<URI> addModel(ModelDto modelDto) {
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
        Model save = modelRepository.save(model);
        return Optional.of(getUri(save::getId, DEFAULT_VARIABLE_PATH));
    }

    @Override
    public URI addProperty(ModelPropertyDto modelPropertyDto, String property) {
        // TODO: 18.05.2021 add verification service
        Optional<ModelProperty> first = Arrays.stream(ModelProperty.values())
                .filter(modelProperty -> modelProperty.toString().equals(property)).findFirst();
        if (first.isPresent()) {
            Long savedId = first.get().doSave(modelPropertyDto);
            return getUri(() -> savedId, DEFAULT_VARIABLE_PATH);

        } else {
            throw new HttpMessageNotWritableException("Invalid property");
        }
    }

    private void checkEngineIsNotEmpty(List<Engine> engines) {
        if (engines.isEmpty())
            throw new NoSuchModelMembersException();
    }


    enum ModelProperty {
        engine, type, segment;
        private Function<ModelPropertyDto, Long> saveOperation;

        public void setSave(Function<ModelPropertyDto, Long> saveOperation) {
            this.saveOperation = saveOperation;
        }

        Long doSave(ModelPropertyDto modelPropertyDto) {
            return saveOperation.apply(modelPropertyDto);
        }
    }
}

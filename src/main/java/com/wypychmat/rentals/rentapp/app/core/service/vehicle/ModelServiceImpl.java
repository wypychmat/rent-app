package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ModelDto;
import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ModelPropertyDto;
import com.wypychmat.rentals.rentapp.app.core.exception.vehicle.NoSuchModelMembersException;
import com.wypychmat.rentals.rentapp.app.core.mapper.NewModelMapper;
import com.wypychmat.rentals.rentapp.app.core.model.projection.ProjectionModel;
import com.wypychmat.rentals.rentapp.app.core.model.projection.ProjectionModelProperty;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.*;
import com.wypychmat.rentals.rentapp.app.core.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
// TODO: 19.05.2021 add verification service
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
    void initializeModelPropertyEnums() {

        ModelProperty.engine.initialize((
                modelPropertyDto -> engineRepository.save(new Engine(modelPropertyDto.getName())).getId()),
                engineRepository::findAllContaining,
                engineRepository::findByIdWithProjection);

        ModelProperty.type.initialize(
                (modelPropertyDto -> typeRepository.save(new Type(modelPropertyDto.getName())).getId()),
                typeRepository::findAllContaining,
                typeRepository::findByIdWithProjection);

        ModelProperty.segment.initialize(
                (modelPropertyDto -> segmentRepository.save(new Segment(modelPropertyDto.getName())).getId()),
                segmentRepository::findAllContaining,
                segmentRepository::findByIdWithProjection);
    }

    @Override
    public Page<ProjectionModel> getAllModel(Pageable pageable, String model) {
        return modelRepository.findAllModels(pageable, model);
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
        return onModelPropertyOperation(
                property,
                (modelProperty) -> getUri(() -> modelProperty.doSave(modelPropertyDto), DEFAULT_VARIABLE_PATH));
    }

    private Optional<ModelProperty> findModelProperty(String property) {
        return Arrays.stream(ModelProperty.values())
                .filter(modelProperty -> modelProperty.toString().equals(property)).findFirst();
    }

    public <R> R onModelPropertyOperation(String property,
                                          Function<ModelProperty, R> operation) {
        Optional<ModelProperty> modelProperty = findModelProperty(property);
        if (modelProperty.isPresent()) {
            return operation.apply(modelProperty.get());
        } else {
            throw new HttpMessageNotWritableException("Invalid property");
        }
    }

    public Page<ProjectionModelProperty> getAllPropertyModel(Pageable pageable, String property, String valueName) {
        return onModelPropertyOperation(
                property,
                (modelProperty) -> modelProperty.doGetByValueName(pageable, valueName));
    }

    @Override
    public Page<ProjectionModel> getAllModelsByProducer(Pageable pageable, Long id) {
        return modelRepository.findAllModelsByManufacturerId(pageable, id);
    }

    @Override
    public Optional<ProjectionModel> getModelById(Long id) {
        return modelRepository.findSingleModelById(id);
    }

    @Override
    public ProjectionModelProperty getModelPropertyById(Long id, String property) {
        return onModelPropertyOperation(
                property,
                (modelProperty) -> modelProperty.doGetById(id));
    }


    private void checkEngineIsNotEmpty(List<Engine> engines) {
        if (engines.isEmpty())
            throw new NoSuchModelMembersException();
    }

    enum ModelProperty {
        engine, type, segment;
        private Function<ModelPropertyDto, Long> saveOperation;
        private BiFunction<Pageable, String, Page<ProjectionModelProperty>> getOperation;
        private Function<Long, ProjectionModelProperty> getByIdOperation;

        public void initialize(Function<ModelPropertyDto, Long> saveOperation,
                               BiFunction<Pageable, String, Page<ProjectionModelProperty>> getOperation,
                               Function<Long, ProjectionModelProperty> getByIdOperation) {
            this.saveOperation = saveOperation;
            this.getOperation = getOperation;
            this.getByIdOperation = getByIdOperation;
        }

        Long doSave(ModelPropertyDto modelPropertyDto) {
            return saveOperation.apply(modelPropertyDto);
        }

        Page<ProjectionModelProperty> doGetByValueName(Pageable pageable, String valueName) {
            return getOperation.apply(pageable, valueName);
        }

        ProjectionModelProperty doGetById(Long id) {
            return getByIdOperation.apply(id);
        }
    }
}

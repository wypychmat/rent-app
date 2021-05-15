package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.model.projection.UniqueModel;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Model;
import com.wypychmat.rentals.rentapp.app.core.repository.ManufacturerRepository;
import com.wypychmat.rentals.rentapp.app.core.repository.ModelRepository;
import com.wypychmat.rentals.rentapp.app.core.repository.SegmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class VehicleServiceImpl implements VehicleService {

    private final ModelRepository modelRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final SegmentRepository segmentRepository;

    VehicleServiceImpl(ModelRepository modelRepository,
                       ManufacturerRepository manufacturerRepository,
                       SegmentRepository segmentRepository) {
        this.modelRepository = modelRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.segmentRepository = segmentRepository;
    }

    @Override
    public Page<Model> getAllUniqueModel(Pageable pageable) {
//        List<UniqueModel> allUniqueModels = modelRepository.findAllUniqueModels();
        String a= "";
        return modelRepository.findAll(pageable);
    }
}

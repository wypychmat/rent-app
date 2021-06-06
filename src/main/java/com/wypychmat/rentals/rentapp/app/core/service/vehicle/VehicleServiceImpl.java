package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.VehicleDto;
import com.wypychmat.rentals.rentapp.app.core.mapper.NewVehicleMapper;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.BaseVehicleProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.VehicleProjection;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Engine;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Model;
import com.wypychmat.rentals.rentapp.app.core.model.rent.RentStatus;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Vehicle;
import com.wypychmat.rentals.rentapp.app.core.repository.ModelRepository;
import com.wypychmat.rentals.rentapp.app.core.repository.VehicleRepository;
import com.wypychmat.rentals.rentapp.app.core.util.Constant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.net.URI;
import java.util.Optional;

@Service
// TODO: 21.05.2021 add Verification
class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final ModelRepository modelRepository;
    private final NewVehicleMapper newVehicleMapper;

    public VehicleServiceImpl(VehicleRepository vehicleRepository,
                              ModelRepository modelRepository,
                              NewVehicleMapper newVehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.modelRepository = modelRepository;
        this.newVehicleMapper = newVehicleMapper;
    }

    @Override
    public Page<VehicleProjection> getVehicles(Pageable pageable, String modelId, RentStatus rentStatus) {
        // TODO: 21.05.2021 verification before parseLong
        return vehicleRepository.findAllVehiclesByModelId(pageable, Long.parseLong(modelId), rentStatus);
    }

    @Override
    @Transactional
    public URI addVehicle(VehicleDto vehicleDto) {
        // TODO: 21.05.2021 verification and exception with handler
        Model model = modelRepository.findById(vehicleDto.getModelId())
                .orElseThrow();
        Engine engine = model.getEngines().stream()
                .filter(e -> e.getId().equals(vehicleDto.getEngineId()))
                .findAny()
                .orElseThrow();
        Vehicle save = vehicleRepository.save(newVehicleMapper.toVehicle(vehicleDto, model, engine));
        return getUri(save::getId, Constant.DEFAULT_VARIABLE_PATH);

    }

    @Override
    // TODO: 22.05.2021 add verification
    public Optional<BaseVehicleProjection> getVehicleBaseInformationByPlate(String registrationPlate) {
        return vehicleRepository.getByRegistrationPlate(registrationPlate);
    }

}

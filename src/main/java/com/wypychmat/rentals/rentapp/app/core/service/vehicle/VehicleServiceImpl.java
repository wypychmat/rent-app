package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.VehicleDto;
import com.wypychmat.rentals.rentapp.app.core.mapper.NewVehicleMapper;
import com.wypychmat.rentals.rentapp.app.core.model.projection.VehicleProjection;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Engine;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Model;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.RentStatus;
import com.wypychmat.rentals.rentapp.app.core.repository.ModelRepository;
import com.wypychmat.rentals.rentapp.app.core.repository.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
// TODO: 21.05.2021 add Verification
public class VehicleServiceImpl implements VehicleService {

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
    public void addVehicle(VehicleDto vehicleDto) {
        // TODO: 21.05.2021 verification and exception with handler
        Model model = modelRepository.findById(vehicleDto.getModelId())
                .orElseThrow();
        Engine engine = model.getEngines().stream()
                .filter(e -> e.getId().equals(vehicleDto.getEngineId()))
                .findAny()
                .orElseThrow();
        vehicleRepository.save(newVehicleMapper.toVehicle(vehicleDto, model, engine));
    }
}

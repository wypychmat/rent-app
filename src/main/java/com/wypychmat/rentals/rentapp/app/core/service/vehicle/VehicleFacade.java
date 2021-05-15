package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VehicleFacade {
    private final VehicleService vehicleService;

    public VehicleFacade(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

   public Page<Model> getAllUniqueModel(Pageable pageable) {
        return vehicleService.getAllUniqueModel(pageable);
    }
}

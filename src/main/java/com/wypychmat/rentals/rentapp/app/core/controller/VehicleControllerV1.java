package com.wypychmat.rentals.rentapp.app.core.controller;


import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Manufacturer;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Segment;
import com.wypychmat.rentals.rentapp.app.core.repository.ManufacturerRepository;
import com.wypychmat.rentals.rentapp.app.core.repository.ModelRepository;
import com.wypychmat.rentals.rentapp.app.core.repository.SegmentRepository;
import com.wypychmat.rentals.rentapp.app.core.service.vehicle.VehicleFacade;
import com.wypychmat.rentals.rentapp.app.core.util.ApiVersion;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "${api.base}" + "${api.path.vehicles}", produces = {ApiVersion.JSON, ApiVersion.V1_JSON})
public class VehicleControllerV1 {

    private final VehicleFacade vehicleFacade;
    private final ModelRepository modelRepository;
    private final SegmentRepository segmentRepository;
    private final ManufacturerRepository manufacturerRepository;

    public VehicleControllerV1(VehicleFacade vehicleFacade, ModelRepository modelRepository, SegmentRepository segmentRepository, ManufacturerRepository manufacturerRepository) {
        this.vehicleFacade = vehicleFacade;
        this.modelRepository = modelRepository;
        this.segmentRepository = segmentRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @GetMapping("/all")
    ResponseEntity<?> getAllModel(Pageable pageable) {
        vehicleFacade.getAllUniqueModel(pageable);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/add")
    ResponseEntity<?> addManufacturer() {


        return ResponseEntity.ok().build();
    }
}

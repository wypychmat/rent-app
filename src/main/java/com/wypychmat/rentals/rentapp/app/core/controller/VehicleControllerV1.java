package com.wypychmat.rentals.rentapp.app.core.controller;


import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ModelDto;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.*;
import com.wypychmat.rentals.rentapp.app.core.repository.*;
import com.wypychmat.rentals.rentapp.app.core.service.vehicle.VehicleFacade;
import com.wypychmat.rentals.rentapp.app.core.util.ApiVersion;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "${api.base}" + "${api.path.vehicles}", produces = {ApiVersion.JSON, ApiVersion.V1_JSON})
public class VehicleControllerV1 {

    private final VehicleFacade vehicleFacade;

    public VehicleControllerV1(VehicleFacade vehicleFacade) {
        this.vehicleFacade = vehicleFacade;

    }

    @GetMapping("/all")
    ResponseEntity<?> getAllModel(Pageable pageable) {
        vehicleFacade.getAllUniqueModel(pageable);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/addMan")
    ResponseEntity<?> addModel(@RequestBody ModelDto modelDto) {
        vehicleFacade.addModel(modelDto);
        return ResponseEntity.ok().build();
    }
}

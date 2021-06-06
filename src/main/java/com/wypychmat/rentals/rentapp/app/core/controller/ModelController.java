package com.wypychmat.rentals.rentapp.app.core.controller;


import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ModelDto;
import com.wypychmat.rentals.rentapp.app.core.dto.vehicle.ModelPropertyDto;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ProjectionModel;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ProjectionModelProperty;
import com.wypychmat.rentals.rentapp.app.core.service.vehicle.ModelFacade;
import com.wypychmat.rentals.rentapp.app.core.util.ApiVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.function.Supplier;

@RestController
@RequestMapping(value = "${api.base}" + "${api.path.models}", produces = {ApiVersion.JSON, ApiVersion.V1_JSON})
public class ModelController {

    private final ModelFacade modelFacade;

    public ModelController(ModelFacade modelFacade) {
        this.modelFacade = modelFacade;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('read')")
    Page<ProjectionModel> getAllModel(Pageable pageable, @RequestParam(defaultValue = "") String model) {
        return modelFacade.getAllModel(pageable, model);
    }

    @GetMapping("/byProducer/{id}")
    @PreAuthorize("hasAuthority('read')")
    Page<ProjectionModel> getAllModelByManufacturer(Pageable pageable, @PathVariable(name = "id") Long id) {
        return modelFacade.getAllModelsByProducer(pageable, id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read')")
    ResponseEntity<ProjectionModel> getModelById(@PathVariable Long id) {
        Optional<ProjectionModel> modelById = modelFacade.getModelById(id);
        return modelById.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/properties")
    @PreAuthorize("hasAuthority('read')")
    Page<ProjectionModelProperty> getModelProperties(Pageable pageable,
                                                     @RequestParam String property,
                                                     @RequestParam(defaultValue = "") String propertyValue) {
        return modelFacade.getAllModelProperty(pageable, property, propertyValue);
    }

    @GetMapping("/{id}/properties")
    @PreAuthorize("hasAuthority('read')")
    ProjectionModelProperty getModelPropertyById(@PathVariable Long id,
                                                       @RequestParam String property) {
        return modelFacade.getModelPropertyById(id,property);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    ResponseEntity<?> addModel(@RequestBody ModelDto modelDto) {
        return modelFacade.addModel(modelDto).map(value -> ResponseEntity.created(value).build())
                .orElseGet(getConflictResponse());
    }

    @PostMapping("/properties")
    @PreAuthorize("hasAuthority('write')")
    ResponseEntity<?> addModelProperty(@RequestBody ModelPropertyDto propertyDto,
                                       @RequestParam String property) {
        return ResponseEntity.created(modelFacade.addProperty(propertyDto, property)).build();
    }

    private Supplier<ResponseEntity<Object>> getConflictResponse() {
        return () -> ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}

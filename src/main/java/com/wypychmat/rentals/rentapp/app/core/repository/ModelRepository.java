package com.wypychmat.rentals.rentapp.app.core.repository;

import com.wypychmat.rentals.rentapp.app.core.model.projection.ProjectionModel;
import com.wypychmat.rentals.rentapp.app.core.model.projection.ProjectionModelProperty;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ModelRepository extends JpaRepository<Model, Long> {

    @Query(name = "Model.findAll")
    Page<ProjectionModel> findAllModels(Pageable pageable, String modelName);

    @Query(name = "Model.findAllByManufacturerId")
    Page<ProjectionModel> findAllModelsByManufacturerId(Pageable pageable, Long producerId);

    @Query(name = "Model.findByModelId")
    Optional<ProjectionModel> findSingleModelById(Long modelId);


}

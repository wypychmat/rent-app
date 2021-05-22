package com.wypychmat.rentals.rentapp.app.core.repository;

import com.wypychmat.rentals.rentapp.app.core.model.projection.ProjectionModelProperty;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Engine;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EngineRepository extends JpaRepository<Engine, Long> {

    @Query(name = "Engine.getAllContains")
    Page<ProjectionModelProperty> findAllContaining(Pageable pageable, String valueName);

    @Query(name = "Engine.getById")
    ProjectionModelProperty findByIdWithProjection(Long selectedId);
}

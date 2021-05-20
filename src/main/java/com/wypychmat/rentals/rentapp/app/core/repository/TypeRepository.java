package com.wypychmat.rentals.rentapp.app.core.repository;

import com.wypychmat.rentals.rentapp.app.core.model.projection.ProjectionModelProperty;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TypeRepository extends JpaRepository<Type, Long> {

    @Query(name = "Type.getAllContains")
    Page<ProjectionModelProperty> findAllContaining(Pageable pageable, String valueName);

    @Query(name = "Type.getById")
    ProjectionModelProperty findByIdWithProjection(Long selectedId);

}

package com.wypychmat.rentals.rentapp.app.core.repository;

import com.wypychmat.rentals.rentapp.app.core.model.projection.ProjectionModel;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long> {

    @Query("SELECT m.id as id,  mf.manufacturer as manufacturer,  m.model as model, " +
            "m.startProductionYear as startProductionYear,  m.description as description, t.type as type, " +
            "s.segment as segment FROM Model m LEFT JOIN Manufacturer mf on mf.id = m.manufacturer.id LEFT JOIN Type t on " +
            "m.type.id = t.id LEFT JOIN Segment s on m.segment.id = s.id WHERE m.model LIKE %:modelName%")
    Page<ProjectionModel> findAllModels(Pageable pageable, String modelName);


}

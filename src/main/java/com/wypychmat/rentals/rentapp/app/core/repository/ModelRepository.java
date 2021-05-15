package com.wypychmat.rentals.rentapp.app.core.repository;

import com.wypychmat.rentals.rentapp.app.core.model.projection.UniqueModel;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long> {

//    @Query("SELECT DISTINCT m.name as model, mf.name as manufacturer, m.vehicleType as vehicleType, s.name as segment FROM Model m LEFT JOIN m.manufacturer as mf LEFT JOIN m.description as d LEFT JOIN m.segment as s")
//    List<UniqueModel> findAllUniqueModels();
}

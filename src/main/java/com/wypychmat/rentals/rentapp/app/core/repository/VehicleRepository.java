package com.wypychmat.rentals.rentapp.app.core.repository;



import com.wypychmat.rentals.rentapp.app.core.model.projection.VehicleProjection;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.RentStatus;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface VehicleRepository extends JpaRepository<Vehicle, Long> {


    @Query("SELECT v.id as id, m.model as model, mf.manufacturer as producer, v.productionYear as productionYear, " +
            " v.registrationPlate as registrationPlate, v.status as status, e.engine as engine FROM " +
            "Vehicle v LEFT JOIN Model m on m.id = v.model.id LEFT JOIN m.manufacturer mf ON mf.id = m.manufacturer.id " +
            "LEFT JOIN v.engine e ON v.engine.id = e.id WHERE m.id = :modelId AND v.status =:rentStatus")
    Page<VehicleProjection> findAllVehiclesByModelId(Pageable pageable, Long modelId, RentStatus rentStatus);



}

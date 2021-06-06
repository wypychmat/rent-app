package com.wypychmat.rentals.rentapp.app.core.repository;


import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.BaseVehicleProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.VehicleProjection;
import com.wypychmat.rentals.rentapp.app.core.model.rent.RentStatus;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {


    @Query("SELECT v.id as id, m.model as model, mf.manufacturer as producer, v.productionYear as productionYear, " +
            " v.registrationPlate as registrationPlate, v.status as status, e.engine as engine FROM " +
            "Vehicle v LEFT JOIN Model m on m.id = v.model.id LEFT JOIN m.manufacturer mf ON mf.id = m.manufacturer.id " +
            "LEFT JOIN v.engine e ON v.engine.id = e.id WHERE m.id = :modelId AND v.status =:rentStatus")
    Page<VehicleProjection> findAllVehiclesByModelId(Pageable pageable, Long modelId, RentStatus rentStatus);

    @Query("SELECT v.id as id, v.registrationPlate as registrationPlate, m.model as model, v.status as rentStatus" +
            " FROM Vehicle v LEFT JOIN v.model m on v.model.id = m.id WHERE v.registrationPlate =:registrationPlate")
    Optional<BaseVehicleProjection> getByRegistrationPlate(@NotBlank String registrationPlate);


    @Modifying
    @Query("UPDATE Vehicle v SET v.status= :newStatus WHERE v.id = :id")
    void changeStatus(RentStatus newStatus, Long id);
}

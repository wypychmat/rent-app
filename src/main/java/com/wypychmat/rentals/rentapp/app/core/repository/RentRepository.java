package com.wypychmat.rentals.rentapp.app.core.repository;

import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ManageRentProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.UserRentProjection;
import com.wypychmat.rentals.rentapp.app.core.model.rent.RentHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RentRepository extends JpaRepository<RentHistory, Long> {


    @Query(value = "SELECT mf.manufacturer as manufacturer,m.model as model, r.hireDate as rentDate, " +
            "r.odometerStart as odometerStart, r.odometerEnd as odometerEnd FROM RentHistory r" +
            " LEFT JOIN r.vehicle v on r.vehicle.id = v.id LEFT JOIN v.model m ON m.id = v.model.id LEFT JOIN" +
            " m.manufacturer mf ON m.manufacturer.id = mf.id WHERE r.user.id = :userId")
    Page<UserRentProjection> getRentHistories(Pageable pageable, Long userId);


    @Query(value = "SELECT r.id as rentId, r.vehicle.id as vehicleId, r.user.id as userId, r.hireDate as hireDate," +
            " r.odometerStart as odometerStart," +
            "r.returnDate as returnDate, r.odometerEnd as odometerEnd  FROM RentHistory r WHERE r.user.id =:userId " +
            "AND CONCAT(r.id, '') LIKE %:rentId%")
    Page<ManageRentProjection> getRentHistoriesAsManager(Pageable pageable, Long userId, String rentId);

    Boolean existsByIdAndUserIdAndVehicleId(Long id, Long userId, Long vehicleId);

    @Query(value = "UPDATE RentHistory r SET r.odometerEnd=:odometerEnd, r.returnDate=:returnDate WHERE r.id=:returnId")
    void updateOdometerAndRentDate(Long odometer, Long returnId, Date returnDate);


}

package com.wypychmat.rentals.rentapp.app.core.repository;



import com.wypychmat.rentals.rentapp.app.core.model.projection.ManufacturerProjection;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Manufacturer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ManufacturerRepository extends JpaRepository<Manufacturer,Long> {

    @Query(value = "SELECT m.id as id, m.manufacturer as manufacturer, m.countryCode as countryCode FROM " +
            "Manufacturer m WHERE UPPER(m.manufacturer) LIKE %:manufacturer%")
    Page<ManufacturerProjection> findAllManufacturer(Pageable pageable, String manufacturer);
}

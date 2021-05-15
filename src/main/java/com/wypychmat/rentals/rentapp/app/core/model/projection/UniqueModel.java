package com.wypychmat.rentals.rentapp.app.core.model.projection;


import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Segment;

public interface UniqueModel {

    String getModel();

    String getManufacturer();

//    Integer getMinProductionYear();

    Segment getSegment();

//    List<Engine> getEngines();

    String getDescription();


}

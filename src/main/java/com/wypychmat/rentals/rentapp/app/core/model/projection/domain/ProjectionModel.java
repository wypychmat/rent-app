package com.wypychmat.rentals.rentapp.app.core.model.projection.domain;


public interface ProjectionModel {
    Long getId();
    String getManufacturer();
    String getModel();
    Integer getStartProductionYear();
    String getDescription();
    String getType();
    String getSegment();
}

package com.wypychmat.rentals.rentapp.app.core.dto.vehicle;

import java.util.List;

// TODO: 16.05.2021 add validation constrains
public class ModelDto {
    private List<Long> enginesId;
    private Long manufacturerId;
    private Long segmentId;
    private Long typeId;
    private Integer startProductionYear;
    private String model;
    private String description;


    public List<Long> getEnginesId() {
        return enginesId;
    }

    public void setEnginesId(List<Long> enginesId) {
        this.enginesId = enginesId;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Long getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(Long segmentId) {
        this.segmentId = segmentId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStartProductionYear() {
        return startProductionYear;
    }

    public void setStartProductionYear(Integer startProductionYear) {
        this.startProductionYear = startProductionYear;
    }
}

package com.wypychmat.rentals.rentapp.app.core.dto.vehicle;

public class ModelPropertyDto {
    private String name;

    public String getName() {
        return name.toUpperCase();
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.wypychmat.rentals.rentapp.app.core.dto.vehicle;

public class ManufacturerDto {

    private String manufacturer;
    private String countryCode;


    public String getManufacturer() {
        return manufacturer;
    }

    public ManufacturerDto setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public ManufacturerDto setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }
}

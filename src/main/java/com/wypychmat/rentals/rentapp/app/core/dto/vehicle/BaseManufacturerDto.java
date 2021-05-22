package com.wypychmat.rentals.rentapp.app.core.dto.vehicle;

public class BaseManufacturerDto {

    protected String manufacturer;
    protected String countryCode;


    public String getManufacturer() {
        return manufacturer;
    }

    public BaseManufacturerDto setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public BaseManufacturerDto setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }
}

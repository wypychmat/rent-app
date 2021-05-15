package com.wypychmat.rentals.rentapp.app.core.model.vehicle;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String manufacturer;

    @NotBlank
    private String countryCode;

    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Model> models = new HashSet<>();

    public Manufacturer() {
    }

    public Manufacturer(String manufacturer, String countryCode) {
        this.manufacturer = manufacturer;
        this.countryCode = countryCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Set<Model> getModels() {
        return models;
    }

    public void addModels(Model... models){
        this.models.addAll(Arrays.asList(models));
    }
}

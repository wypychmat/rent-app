package com.wypychmat.rentals.rentapp.app.core.model.vehicle;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Engine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String engine;

    public Engine() {
    }

    public Engine(String engine) {
        this.engine = engine;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }
}

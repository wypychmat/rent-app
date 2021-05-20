package com.wypychmat.rentals.rentapp.app.core.model.vehicle;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@NamedQueries({
        @NamedQuery(name = "Engine.getAllContains", query = Engine.SELECT_ALL
                + "WHERE e.engine LIKE concat('%', :valueName, '%')"),

        @NamedQuery(name = "Engine.getById", query = Engine.SELECT_ALL
                + "WHERE e.id = :selectedId")
})
public class Engine {
    static final String SELECT_ALL = "SELECT e.id as id, e.engine as name FROM Engine e ";
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

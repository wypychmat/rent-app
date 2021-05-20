package com.wypychmat.rentals.rentapp.app.core.model.vehicle;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@NamedQueries({
        @NamedQuery(name = "Type.getAllContains", query = Type.SELECT_ALL
                + "WHERE t.type LIKE concat('%', :valueName, '%')"),

        @NamedQuery(name = "Type.getById", query = Type.SELECT_ALL
                + "WHERE t.id = :selectedId")
})
public class Type {
    static final String SELECT_ALL = "SELECT t.id as id, t.type as name FROM Type t ";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String type;

    public Type() {
    }

    public Type(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

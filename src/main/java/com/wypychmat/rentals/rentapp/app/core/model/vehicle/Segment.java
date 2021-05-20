package com.wypychmat.rentals.rentapp.app.core.model.vehicle;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@NamedQueries({
        @NamedQuery(name = "Segment.getAllContains", query = Segment.SELECT_ALL
                + "WHERE s.segment LIKE concat('%', :valueName, '%')"),

        @NamedQuery(name = "Segment.getById", query = Segment.SELECT_ALL
                + "WHERE s.id = :selectedId")
})
public class Segment {
    static final String SELECT_ALL = "SELECT s.id as id, s.segment as name FROM Segment s ";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String segment;

    public Segment() {
    }

    public Segment(String segment) {
        this.segment = segment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

}

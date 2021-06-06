package com.wypychmat.rentals.rentapp.app.core.model.vehicle;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "Model.findAll",query = Model.ALL_MODELS + "WHERE m.model LIKE CONCAT('%', :modelName, '%')"
        ),
        @NamedQuery(name = "Model.findAllByManufacturerId",query = Model.ALL_MODELS + "WHERE mf.id = :producerId"
        ),
        @NamedQuery(name = "Model.findByModelId",query = Model.ALL_MODELS + "WHERE m.id = :modelId"
        )
})
public class Model {
    static final String ALL_MODELS = "SELECT m.id as id,  mf.manufacturer as manufacturer,  m.model as model, " +
            "m.startProductionYear as startProductionYear,  m.description as description, t.type as type, " +
            "s.segment as segment FROM Model m LEFT JOIN Manufacturer mf on mf.id = m.manufacturer.id LEFT JOIN Type t on " +
            "m.type.id = t.id LEFT JOIN Segment s on m.segment.id = s.id ";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String model;

    @NotNull
    @Min(value = 1900)
    private Integer startProductionYear;

    @NotBlank
    @Size(max = 255)
    private String description;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "id")
    @NotNull
    private Manufacturer manufacturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    @NotNull
    private Type type;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "segment_id", referencedColumnName = "id")
    @NotNull
    private Segment segment;

    @ManyToMany
    @JoinTable(name = "model_engines", joinColumns =
    @JoinColumn(name = "model_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "engine_id", referencedColumnName = "id"))
    @NotEmpty
    private final Set<Engine> engines = new HashSet<>();

    @OneToMany(mappedBy = "model", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private final Set<Vehicle> vehicles = new HashSet<>();

    public Model() {
    }

    public Model(String model,
                 Integer startProductionYear,
                 String description,
                 Manufacturer manufacturer,
                 Type type,
                 Segment segment,
                 List<Engine> engines) {
        this.model = model;
        this.startProductionYear = startProductionYear;
        this.description = description;
        this.manufacturer = manufacturer;
        this.type = type;
        this.segment = segment;
        this.engines.addAll(engines);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getStartProductionYear() {
        return startProductionYear;
    }

    public void setStartProductionYear(Integer startProductionYear) {
        this.startProductionYear = startProductionYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public Set<Engine> getEngines() {
        return engines;
    }

    public void addEngines(Engine... engines) {
        this.engines.addAll(Arrays.asList(engines));
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void addVehicles(Vehicle... vehicles) {
        this.vehicles.addAll(Arrays.asList(vehicles));
    }

}

package com.wypychmat.rentals.rentapp.app.core.model.vehicle;

import com.wypychmat.rentals.rentapp.app.core.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class BookHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @NotNull
    private User user;

    @NotNull
    private Date bookDate;

    @ManyToOne
    @JoinColumn(name = "vehicle_id",referencedColumnName = "id")
    @NotNull
    private Vehicle vehicle;

    @Column(columnDefinition = "bit(1) default 0")
    private boolean wasAccomplished;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isWasAccomplished() {
        return wasAccomplished;
    }

    public void setWasAccomplished(boolean wasAccomplished) {
        this.wasAccomplished = wasAccomplished;
    }
}

package com.wypychmat.rentals.rentapp.app.core.model.rent;

import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Vehicle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.Period;
import java.util.Date;

@Entity
public class BookHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    private User user;

    @NotNull
    private Date bookDate;

    @NotNull
    private Date startRentDate;

    @NotNull
    private Date endRentDate;

    @NotNull
    private Date expiresDate;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    @NotNull
    private Vehicle vehicle;

    @Column(columnDefinition = "bit(1) default 0")
    private boolean wasAccomplished;

    public BookHistory() {
    }

    public BookHistory(User user,
                       Date startRentDate, Date endRentDate, Vehicle vehicle,
                       int expireAfterDay) {
        this.startRentDate = startRentDate;
        this.endRentDate = endRentDate;
        Instant now = Instant.now();
        this.user = user;
        this.bookDate = Date.from(now);
        this.vehicle = vehicle;
        this.wasAccomplished = false;
        vehicle.setStatus(RentStatus.BOOKED);
        expiresDate = Date.from(now.plus(Period.ofDays(expireAfterDay)));
    }

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

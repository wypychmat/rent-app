package com.wypychmat.rentals.rentapp.app.core.model.vehicle;

import com.wypychmat.rentals.rentapp.app.core.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class RentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id",referencedColumnName = "id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @NotNull
    private Date hireDate;

    @NotNull
    private Long odometerStart;

    private Date returnDate;

    private Long odometerEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Long getOdometerStart() {
        return odometerStart;
    }

    public void setOdometerStart(Long odometerStart) {
        this.odometerStart = odometerStart;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Long getOdometerEnd() {
        return odometerEnd;
    }

    public void setOdometerEnd(Long odometerEnd) {
        this.odometerEnd = odometerEnd;
    }
}

package com.wypychmat.rentals.rentapp.app.core.model.builder;

import com.wypychmat.rentals.rentapp.app.core.model.rent.RentHistory;
import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Vehicle;

import java.util.Date;


public class RentHistoryBuilder {

    private RentHistoryBuilder() {
    }

    private Long vehicleId;


    private Long userId;


    private Long odometer;


    public static RentHistoryBuilder builder() {
        return new RentHistoryBuilder();
    }


    public RentHistoryBuilder setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public RentHistoryBuilder setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public RentHistoryBuilder setOdometer(Long odometer) {
        this.odometer = odometer;
        return this;
    }

    public RentHistory build() {
        RentHistory rentHistory = new RentHistory();
        rentHistory.setHireDate(new Date());
        rentHistory.setUser(new User(userId));
        rentHistory.setVehicle(new Vehicle(vehicleId));
        rentHistory.setOdometerStart(odometer);
        return rentHistory;
    }
}

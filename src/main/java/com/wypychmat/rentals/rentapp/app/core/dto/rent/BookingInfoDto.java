package com.wypychmat.rentals.rentapp.app.core.dto.rent;

import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Vehicle;

import java.util.Date;

public class BookingInfoDto {


    private Long id;


    private User user;


    private Date bookDate;


    private Date startRentDate;


    private Date endRentDate;


    private Date expiresDate;


    private Vehicle vehicle;
}

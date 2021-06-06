package com.wypychmat.rentals.rentapp.app.core.dto.rent;

import java.util.Date;

public interface ManageRentDto {


    Long getRentId();

    Long getUserId();

    Long getVehicleId();

    Date getHireDate();

    Date getReturnDateDate();

    Long getOdometerStart();

    Long getOdometerEnd();
}

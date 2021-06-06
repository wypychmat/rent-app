package com.wypychmat.rentals.rentapp.app.core.model.projection.domain;

import java.util.Date;

public interface ManageRentProjection {


    Long getRentId();

    Long getUserId();

    Long getVehicleId();

    Date getHireDate();

    Date getReturnDateDate();

    Long getOdometerStart();

    Long getOdometerEnd();
}

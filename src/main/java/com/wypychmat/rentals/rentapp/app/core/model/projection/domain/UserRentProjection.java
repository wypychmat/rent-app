package com.wypychmat.rentals.rentapp.app.core.model.rent;

import java.util.Date;

public interface UserRentProjection {

    String getManufacturer();

    String getModel();

    Date getRentDate();

    Long getOdometerStart();

    Long getOdometerEnd();
}

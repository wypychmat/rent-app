package com.wypychmat.rentals.rentapp.app.core.model.projection.domain;

import java.util.Date;

public interface ExtendedBookingProjection extends BookingIdProjection {

    Date getBookDate();

    Date getStartRentDate();

    Date getEndRentDate();

    Date getExpiresDate();

    String getManufacturer();

    String getModel();

    String getRegistrationPlate();

}

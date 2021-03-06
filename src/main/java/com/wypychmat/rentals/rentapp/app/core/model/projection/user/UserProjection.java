package com.wypychmat.rentals.rentapp.app.core.model.projection.user;

import java.util.Set;

public interface UserProjection {
    Long getId();

    String getUsername();

    String getEmail();

    String getLastName();

    String getFirstName();

    Boolean getEnabled();

}

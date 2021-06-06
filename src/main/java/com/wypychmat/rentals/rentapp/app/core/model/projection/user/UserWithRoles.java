package com.wypychmat.rentals.rentapp.app.core.model.projection;

public interface UserWithRoles {
    Long getId();
    String getUsername();
    String getRoles();
    String getEmail();
    Boolean getEnabled();

}

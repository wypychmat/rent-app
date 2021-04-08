package com.wypychmat.rentals.rentapp.app.core.model.user;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity(name = "permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotBlank
    private ApplicationPermission permissionName;

    public Permission() {
    }

    public Permission(ApplicationPermission permissionName) {
        this.permissionName = permissionName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationPermission getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(ApplicationPermission permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(id, that.id) && permissionName == that.permissionName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, permissionName);
    }
}

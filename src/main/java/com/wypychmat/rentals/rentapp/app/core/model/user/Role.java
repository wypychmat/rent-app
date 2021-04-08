package com.wypychmat.rentals.rentapp.app.core.model.user;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String roleName;

    @NotBlank
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"),
    inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> rolePermissions = new HashSet<>();

    public Role() {
    }

    public Role(String roleName, Set<Permission> rolePermissions) {
        this.roleName = roleName;
        this.rolePermissions = rolePermissions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setRoleName(ApplicationMainRole roleName) {
        this.roleName = roleName.name();
    }

    public Set<Permission> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(Set<Permission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    public void addRoles(Permission ... permissions) {
        this.rolePermissions.addAll(Arrays.asList(permissions));
    }

    public void addRoles(List<Permission> permissions) {
        this.rolePermissions.addAll(permissions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(roleName, role.roleName) && Objects.equals(rolePermissions, role.rolePermissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName, rolePermissions);
    }
}

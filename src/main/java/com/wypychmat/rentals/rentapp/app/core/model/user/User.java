package com.wypychmat.rentals.rentapp.app.core.model.user;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

@Entity()
@NamedQueries({
        @NamedQuery(name = "User.existByUsername",
                query = "SELECT (COUNT(u)> 0) FROM User u WHERE u.username=:username"),
        @NamedQuery(name = "User.existByUsernameOrEmail",
                query = "SELECT u.username as username, u.email as email FROM User u WHERE u.username=:username OR u.email=:email"),
        @NamedQuery(name = "User.enableUserById",
                query = "UPDATE User u SET u.isEnabled = 1 WHERE  u.id=:id")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    @Size(max = 45)
    private String username;

    @NotBlank
    @Size(min = 6)
    private String password;

    @Email
    @NotBlank
    @Column(unique = true)
    @Size(max = 70)
    private String email;

    @Size(max = 45)
    private String firstName;

    @Size(max = 60)
    private String lastName;

    private boolean isEnabled;

    @NotEmpty
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> userRoles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<RegisterToken> registerTokens;

    public User() {
    }

    public User(String username, String password, String email, String firstName, String lastName, boolean isEnabled, Set<Role> userRoles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isEnabled = isEnabled;
        this.userRoles = userRoles;
    }

    public User(String username, String password, String email, String firstName, String lastName, boolean isEnabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isEnabled = isEnabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Set<Role> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public void addRoles(Role... permissions) {
        this.userRoles.addAll(Arrays.asList(permissions));
    }

    public void addRoles(List<Role> permissions) {
        this.userRoles.addAll(permissions);
    }

    public void setRegisterTokens(List<RegisterToken> registerTokens) {
        this.registerTokens = registerTokens;
    }

    public List<RegisterToken> getRegisterTokens() {
        return registerTokens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isEnabled == user.isEnabled && Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, firstName, lastName, isEnabled);
    }
}

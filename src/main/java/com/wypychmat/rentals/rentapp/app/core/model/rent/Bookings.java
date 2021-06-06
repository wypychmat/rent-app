package com.wypychmat.rentals.rentapp.app.core.model.rent;

import com.wypychmat.rentals.rentapp.app.core.model.user.User;
import com.wypychmat.rentals.rentapp.app.core.model.vehicle.Vehicle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.Period;
import java.util.Date;

import static com.wypychmat.rentals.rentapp.app.core.model.rent.Bookings.ALL_QUERY;

@Entity
@NamedQueries({
        @NamedQuery(name = "Bookings.getAllBookings",query = ALL_QUERY),
        @NamedQuery(name = "Bookings.getByBookIdAndUserId",query = ALL_QUERY + " WHERE b.id =:bookId AND b.user.id=:userId")
})
public class Bookings {
    static final String ALL_QUERY = "SELECT b.id as bookId, b.user.id as userId, v.id as vehicleId, b.bookDate as bookDate, b.startRentDate as startRentDate," +
            " b.endRentDate as endRentDate, b.expiresDate as expiresDate, mf.manufacturer as manufacturer, " +
            "m.model as model, v.registrationPlate as registrationPlate FROM Bookings b" +
            "  LEFT JOIN b.vehicle v ON v.id = b.vehicle.id LEFT join v.model m " +
            "ON v.model.id = m.id LEFT JOIN m.manufacturer mf ON m.manufacturer.id = mf.id";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    private User user;

    @NotNull
    private Date bookDate;

    @NotNull
    private Date startRentDate;

    @NotNull
    private Date endRentDate;

    @NotNull
    private Date expiresDate;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    @NotNull
    private Vehicle vehicle;


    public Bookings() {
    }

    public Bookings(User user,
                    Date startRentDate, Date endRentDate, Vehicle vehicle,
                    int expireAfterDay) {
        this.startRentDate = startRentDate;
        this.endRentDate = endRentDate;
        Instant now = Instant.now();
        this.user = user;
        this.bookDate = Date.from(now);
        this.vehicle = vehicle;
        vehicle.setStatus(RentStatus.BOOKED);
        expiresDate = Date.from(now.plus(Period.ofDays(expireAfterDay)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

}

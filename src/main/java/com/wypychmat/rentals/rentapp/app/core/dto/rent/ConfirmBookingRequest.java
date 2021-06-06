package com.wypychmat.rentals.rentapp.app.core.dto.rent;
// todo add validation
public class ConfirmBookingRequest {
    private Long userId;
    private Long bookId;
    private Long odometer;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getOdometer() {
        return odometer;
    }

    public void setOdometer(Long odometer) {
        this.odometer = odometer;
    }
}

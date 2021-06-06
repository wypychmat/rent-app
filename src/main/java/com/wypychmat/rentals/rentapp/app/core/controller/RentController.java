package com.wypychmat.rentals.rentapp.app.core.controller;


import com.wypychmat.rentals.rentapp.app.core.dto.rent.BookingRequest;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.UserRentProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ExtendedBookingProjection;
import com.wypychmat.rentals.rentapp.app.core.service.rent.RentFacade;
import com.wypychmat.rentals.rentapp.app.core.util.ApiVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
//    @PreAuthorize("hasAuthority('rent')")
@RequestMapping(path = "${api.base}" + "${api.path.rent}", produces = {ApiVersion.JSON, ApiVersion.V1_JSON})
public class RentController {

    private final RentFacade rentFacade;

    public RentController(RentFacade rentFacade) {
        this.rentFacade = rentFacade;
    }

    @PostMapping("/book")
    public void bookVehicle(@RequestBody BookingRequest bookingRequest) {

        rentFacade.bookVehicle(bookingRequest);
        // todo add return value
    }

    @DeleteMapping("booking/cancel/{id}")
    public void cancelBooking(@PathVariable Long id) {

        rentFacade.cancelBooking(id);
        // todo add return value
    }

    @GetMapping("/booking/{bookingId}")
    @PreAuthorize("hasAuthority('manage') or (@checkUser.canAccess(authentication,#userId) and hasAuthority('rent'))")
    public ResponseEntity<ExtendedBookingProjection> getBooking(@PathVariable Long bookingId,
                                                                @RequestParam(required = false) Long userId) {

        Optional<ExtendedBookingProjection> bookingInfo = rentFacade.getBookingInfo(bookingId, userId);

        return bookingInfo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/history")
    public Page<UserRentProjection> getRentHistory(Pageable pageable) {
        return rentFacade.getRentHistories(pageable);
    }


}

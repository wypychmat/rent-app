package com.wypychmat.rentals.rentapp.app.core.controller;


import com.wypychmat.rentals.rentapp.app.core.dto.rent.ConfirmBookingRequest;
import com.wypychmat.rentals.rentapp.app.core.dto.rent.ReturnedVehicle;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ManageRentProjection;
import com.wypychmat.rentals.rentapp.app.core.model.projection.domain.ExtendedBookingProjection;
import com.wypychmat.rentals.rentapp.app.core.service.rent.RentFacade;
import com.wypychmat.rentals.rentapp.app.core.util.ApiVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "${api.base}" + "${api.path.manage}" + "/" + "${api.path.rent}",
        produces = {ApiVersion.JSON, ApiVersion.V1_JSON})
@PreAuthorize("hasAuthority('manage')")
@RestController
public class ManageRentController {

    private final RentFacade rentFacade;

    public ManageRentController(RentFacade rentFacade) {
        this.rentFacade = rentFacade;
    }

    @DeleteMapping("/cancel/{id}")
    public void cancelBookingByManager(@PathVariable Long id) {
        rentFacade.cancelBookingByManager(id);
    }

    @GetMapping
    public Page<ExtendedBookingProjection> getBookings(Pageable pageable) {
        return rentFacade.getBookings(pageable);
    }

    @PostMapping("/confirm")
    public void confirmRental(@RequestBody ConfirmBookingRequest confirmBookingRequest) {

        rentFacade.confirmRent(confirmBookingRequest);

        // todo add response
    }


    @GetMapping("/history")
    public Page<ManageRentProjection> getRentHistory(Pageable pageable,
                                                     @RequestParam Long userId,
                                                     @RequestParam(defaultValue = "") String rentId) {
        return rentFacade.getRentHistoriesAsManager(pageable, userId, rentId);
    }

    @PatchMapping("confirm/return")
    public void confirmGetVehicleBack(@RequestBody ReturnedVehicle returnedVehicle) {
        rentFacade.confirmReturnVehicle(returnedVehicle);
    }


}

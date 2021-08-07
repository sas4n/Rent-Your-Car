package com.lnu.RentYourCar.Booking;

import com.lnu.RentYourCar.Listing.IListingService;
import com.lnu.RentYourCar.userInformation.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookingController {
    private final IBookingService bookings;
    private final IListingService listings;

    @Autowired
    public BookingController(IBookingService bookings, IListingService listings) {
        this.bookings = bookings;
        this.listings = listings;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("bookings")
    public String getBookings(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute("bookings", bookings.getBookingsByUser(currentUser));
        model.addAttribute("listings", listings.getListingsByOwner(currentUser));

        return "booking/index";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("booking/{id}/cancel")
    public String cancelBooking(@PathVariable long id, @AuthenticationPrincipal User currentUser) {
        Booking booking = bookings.getBookingById(id);

        if (booking.getCustomer().getId() != currentUser.getId()
                && booking.getListing().getOwner().getId() != currentUser.getId()) {
            return "redirect:/bookings";
        }

        bookings.cancelBooking(id);

        return "redirect:/bookings";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("booking/{id}/accept")
    public String acceptBooking(@PathVariable long id, @AuthenticationPrincipal User currentUser) {
        Booking booking = bookings.getBookingById(id);

        if (booking.getListing().getOwner().getId() != currentUser.getId()) {
            return "redirect:/bookings";
        }

        bookings.acceptBooking(id);

        return "redirect:/bookings";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("booking/{id}/complete")
    public String completeBooking(@PathVariable long id, @AuthenticationPrincipal User currentUser) {
        Booking booking = bookings.getBookingById(id);

        if (booking.getListing().getOwner().getId() != currentUser.getId()) {
            return "redirect:/bookings";
        }

        bookings.completeBooking(id);

        return "redirect:/bookings";
    }
}

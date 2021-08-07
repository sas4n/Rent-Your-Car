package com.lnu.RentYourCar.Booking;

import com.lnu.RentYourCar.Listing.Listing;
import com.lnu.RentYourCar.userInformation.User;

import java.util.List;

public interface IBookingService {
    void bookListing(Listing listing, User user);
    void cancelBooking(long id);
    void acceptBooking(long id);
    void completeBooking(long id);
    Booking getBookingById(long id);
    List<Booking> getBookingsByUser(User user);
}

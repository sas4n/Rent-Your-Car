package com.lnu.RentYourCar.Listing;

import com.lnu.RentYourCar.userInformation.User;

import java.time.LocalDateTime;
import java.util.List;

public interface IListingService {
    List<Listing> getListings();

    void addListing(Listing listing);

    Listing getListing(long id);

    void updateListing(Listing listing);

    void deleteListing(long id);

    List<Listing> getListingsByCityAndDate(String city, LocalDateTime from, LocalDateTime to);

    List<Listing> getListingsByOwner(User owner);
}

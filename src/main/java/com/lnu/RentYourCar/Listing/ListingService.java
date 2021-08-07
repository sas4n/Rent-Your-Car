package com.lnu.RentYourCar.Listing;

import com.lnu.RentYourCar.userInformation.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListingService implements IListingService {
    private ListingRepository repository;

    @Autowired
    public ListingService(ListingRepository repository) {
        this.repository = repository;
    }

    public List<Listing> getListings() {
        List<Listing> listings = repository.findAll();

        return listings.stream()
                .filter(Listing::isNotBooked)
                .collect(Collectors.toList());
    }

    public void addListing(Listing listing) { repository.save(listing);
    }

    public Listing getListing(long id) {
        return repository.findById(id).orElse(null);
    }

    public void updateListing(Listing listing) {
        repository.save(listing);
    }

    public void deleteListing(long id) {
        repository.deleteById(id);
    }

    public List<Listing> getListingsByCityAndDate(String city, LocalDateTime from, LocalDateTime to) {
        return repository.findAllByCityAndFromDateLessThanEqualAndToDateGreaterThanEqual(city, from, to);
    }

    public List<Listing> getListingsByOwner(User owner) {
        return repository.findAllByOwner(owner);
    }


}

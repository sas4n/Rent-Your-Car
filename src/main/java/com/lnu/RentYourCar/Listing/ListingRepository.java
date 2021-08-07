package com.lnu.RentYourCar.Listing;

import com.lnu.RentYourCar.userInformation.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findAllByCity(String city);

    List<Listing> findAllByCityAndFromDateLessThanEqualAndToDateGreaterThanEqual(String city, LocalDateTime from, LocalDateTime to);

    List<Listing> findAllByOwner(User user);
}

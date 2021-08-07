package com.lnu.RentYourCar.Booking;

import com.lnu.RentYourCar.userInformation.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByCustomer(User customer);
}

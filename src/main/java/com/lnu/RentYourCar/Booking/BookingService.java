package com.lnu.RentYourCar.Booking;

import com.lnu.RentYourCar.Listing.Listing;
import com.lnu.RentYourCar.userInformation.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements IBookingService {
    private BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public void bookListing(Listing listing, User user) {
        Booking booking = new Booking();
        booking.setCustomer(user);
        booking.setListing(listing);
        booking.setStatus(BookingStatus.PENDING);
        bookingRepository.save(booking);
    }

    public void cancelBooking(long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    public void acceptBooking(long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setStatus(BookingStatus.ACCEPTED);
        bookingRepository.save(booking);
    }

    public void completeBooking(long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setStatus(BookingStatus.COMPLETED);
        bookingRepository.save(booking);
    }

    public Booking getBookingById(long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Override
    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findAllByCustomer(user);
    }
}

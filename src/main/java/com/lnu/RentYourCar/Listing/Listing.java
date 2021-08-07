package com.lnu.RentYourCar.Listing;

import com.lnu.RentYourCar.Booking.Booking;
import com.lnu.RentYourCar.Booking.BookingStatus;
import com.lnu.RentYourCar.userInformation.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Listing {

    @Id
    @GeneratedValue
    private long id;
    private String carName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime fromDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime toDate;

    private int price;
    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerId")
    private User owner;

    @OneToMany(mappedBy = "listing")
    private List<Booking> bookings;

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Column(nullable = true, length = 64)
    private String photos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isNotBooked() {
        return this.bookings.isEmpty() || this.bookings.stream().allMatch(b -> b.getStatus() == BookingStatus.CANCELLED);
    }

    public Listing() {
    }

    public void setPhotos(String photosOfTheCar) {
        this.photos = photosOfTheCar;
    }

    public String getPhotos() {
        return photos;
    }

    @Transient
    public String getPhotosImagePath() {
        if (photos == null) return null;

        return "/images/" + id + "/" + photos;
    }

    public Listing(String carName, LocalDateTime fromDate, LocalDateTime toDate, int price, String city, User owner) {
        this.carName = carName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.price = price;
        this.city = city;
        this.owner = owner;
    }
}

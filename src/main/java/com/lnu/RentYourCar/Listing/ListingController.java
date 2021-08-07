package com.lnu.RentYourCar.Listing;

import com.lnu.RentYourCar.Booking.IBookingService;
import com.lnu.RentYourCar.ImageStorage.IImageStorageService;
import com.lnu.RentYourCar.userInformation.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class ListingController {
    private IListingService listings;
    private IBookingService bookings;
    private IImageStorageService imageStorage;

    @Autowired
    public ListingController(IListingService listings, IBookingService bookings, IImageStorageService imageStorage) {
        this.listings = listings;
        this.bookings = bookings;
        this.imageStorage = imageStorage;
    }

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal User currentUser) {

        if (currentUser != null) {
            model.addAttribute("currentUsersName", currentUser.getName());
        }
        model.addAttribute("listings", listings.getListings());
        return "listing/index";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("listing/add")
    public String addListing(Model model) {
        model.addAttribute("listing", new Listing());
        return "listing/add";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("listing/add")
    public String addListing(@ModelAttribute Listing listing, @AuthenticationPrincipal User currentUser,
                             @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String photosOfTheCar = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        listing.setPhotos(photosOfTheCar);
        listing.setOwner(currentUser);
        listings.addListing(listing);

        Resource resource = new ClassPathResource("static/images");

        String uploadDir = resource.getFile().getAbsolutePath() + "/" + listing.getId();
        imageStorage.saveFile(uploadDir, photosOfTheCar, multipartFile);

        return "redirect:/";
    }

    @GetMapping("listing/{id}")
    public String getListing(@PathVariable long id, Model model, @AuthenticationPrincipal User currentUser) {
        Listing listing = listings.getListing(id);

        if (currentUser != null && listing.getOwner().getId() == currentUser.getId()) {
            model.addAttribute("IsOwner", true);
        }

        model.addAttribute("listing", listing);
        return "listing/listing";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("listing/{id}/edit")
    public String editListing(@PathVariable long id, Model model, @AuthenticationPrincipal User currentUser) {
        Listing listing = listings.getListing(id);

        if (listing.getOwner().getId() != currentUser.getId()) {
            return "redirect:/";
        }

        model.addAttribute("listing", listing);
        return "listing/edit";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("listing/{id}/edit")
    public String editListing(@ModelAttribute Listing listing, Model model, @AuthenticationPrincipal User currentUser,
                              @RequestParam("image") MultipartFile multipartFile) throws IOException {

        Listing oldListing = listings.getListing(listing.getId());
        if (oldListing.getOwner().getId() != currentUser.getId()) {
            return "redirect:/";
        }

        listing.setPhotos(oldListing.getPhotos());
        listing.setOwner(currentUser);

        String photosOfTheCar = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if (!photosOfTheCar.isEmpty()) {
            listing.setPhotos(photosOfTheCar);

            String uploadDir = "images/" + listing.getId();
            imageStorage.saveFile(uploadDir, photosOfTheCar, multipartFile);
        }


        listings.updateListing(listing);
        model.addAttribute("listing", listing);
        return "redirect:/listing/" + listing.getId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("listing/{id}/delete")
    public String deleteListing(@PathVariable long id, @AuthenticationPrincipal User currentUser) {
        Listing listing = listings.getListing(id);

        if (listing.getOwner().getId() != currentUser.getId()) {
            return "redirect:/";
        }

        listings.deleteListing(id);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("listing/{id}/book")
    public String bookListing(@PathVariable long id, @AuthenticationPrincipal User currentUser) {
        Listing listing = listings.getListing(id);

        if (listing.getOwner().getId() == currentUser.getId()) {
            return "redirect:/";
        }

        bookings.bookListing(listing, currentUser);

        return "redirect:/";
    }

    @GetMapping("search")
    public String search(@RequestParam String city,
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
                         Model model,
                         @AuthenticationPrincipal User currentUser) {
        if (currentUser != null) {
            model.addAttribute("currentUsersName", currentUser.getName());
        }

        model.addAttribute("listings", listings.getListingsByCityAndDate(city, from, to));
        return "listing/index";
    }

}

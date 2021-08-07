package com.lnu.RentYourCar.Seed;

import com.lnu.RentYourCar.Listing.Listing;
import com.lnu.RentYourCar.Listing.ListingRepository;
import com.lnu.RentYourCar.userInformation.User;
import com.lnu.RentYourCar.userInformation.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ExampleDataSeeder {

    @Bean
    CommandLineRunner commandLineRunner(Environment environment, ListingRepository listingRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (environment.getProperty("spring.profiles.active").equals("prod")) {
                return;
            }

            List<User> users = new ArrayList<>();
            User testUser = new User();

            testUser.setName("Test User");
            testUser.setEmail("test@test.com");
            testUser.setPersonalNumber("12341234-1234");
            testUser.setPassword(passwordEncoder.encode("test"));
            users.add(testUser);

            User testUser2 = new User();

            testUser2.setName("Second Test User");
            testUser2.setEmail("test2@test.com");
            testUser2.setPersonalNumber("12341234-1235");
            testUser2.setPassword(passwordEncoder.encode("test"));
            users.add(testUser2);

            userRepository.saveAll(users);
            List<Listing> listings = new ArrayList<>();

            LocalDate date = LocalDate.parse("2021-05-20", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            Listing car = new Listing("Volkswagen Golf", date.atStartOfDay(), date.plusDays(5).atStartOfDay(), 210, "Vaxjo", testUser);
            car.setPhotos("car.png");
            listingRepository.save(car);

            listings.add(new Listing("Toyota Corolla", date.plusMonths(1).atStartOfDay(), date.plusMonths(1).plusDays(5).atStartOfDay(), 250, "Stockholm", testUser));
            listings.add(new Listing("Mercedes CLA", date.plusMonths(2).atStartOfDay(), date.plusMonths(3).atStartOfDay(), 500, "Gothenburg", testUser));
            listings.add(new Listing("Tesla Model Y", date.plusWeeks(2).atStartOfDay(), date.plusWeeks(4).atStartOfDay(), 423, "Vaxjo", testUser));
            listings.add(new Listing("Porsche 911", date.plusMonths(4).atStartOfDay(), date.plusMonths(4).plusWeeks(2).atStartOfDay(), 923, "Vaxjo", testUser));

            listingRepository.saveAll(listings);
        };
    }
}

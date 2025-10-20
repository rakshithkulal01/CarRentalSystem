package com.carrental.service;

import com.carrental.model.Car;
import com.carrental.model.Rental;
import com.carrental.model.User;
import com.carrental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CarService carService; // We need this to update the car's status

    /**
     * Creates a new rental record and marks the car as unavailable.
     * @param user The user renting the car.
     * @param car The car being rented.
     */
    @Transactional // Ensures both operations (save rental, update car) succeed or fail together
    public void createRental(User user, Car car) {
        // 1. Mark the car as unavailable and save it
        car.setAvailable(false);
        carService.saveCar(car);

        // 2. Create and save the new rental record
        Rental rental = new Rental();
        rental.setUser(user);
        rental.setCar(car);
        rentalRepository.save(rental);
    }

    /**
     * Finds all rentals for a specific user.
     * @param user The user whose rentals to find.
     * @return A list of Rental objects.
     */
    public List<Rental> findRentalsByUser(User user) {
        return rentalRepository.findByUser(user);
    }

    public List<Rental> findAllRentals() {
        return rentalRepository.findAll();
    }
}
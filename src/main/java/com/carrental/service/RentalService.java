package com.carrental.service;

import com.carrental.model.Car;
import com.carrental.model.Rental;
import com.carrental.model.User;
import com.carrental.repository.CarRepository; // Need CarRepository for revenue calculation
import com.carrental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository; // Added for revenue calculation

    /**
     * Creates a new rental record and marks the car as unavailable.
     * @param user The user renting the car.
     * @param car The car being rented.
     * @param startDate The start date of the rental.
     * @param endDate The end date of the rental.
     */
    @Transactional
    public void createRental(User user, Car car, LocalDate startDate, LocalDate endDate) {
        // 1. Mark the car as unavailable and save it
        car.setAvailable(false);
        carService.saveCar(car);

        // 2. Create and save the new rental record with dates
        Rental rental = new Rental();
        rental.setUser(user);
        rental.setCar(car);
        rental.setStartDate(startDate);
        rental.setEndDate(endDate);
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

    /**
     * Finds all rentals in the system.
     * @return A list of all Rental objects.
     */
    public List<Rental> findAllRentals() {
        return rentalRepository.findAll();
    }

    /**
     * Marks a car associated with a rental as returned (available).
     * @param rentalId The ID of the rental to process.
     * @return true if successful, false otherwise.
     */
    @Transactional
    public boolean returnRental(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId).orElse(null);

        if (rental != null && !rental.getCar().isAvailable()) {
            Car car = rental.getCar();
            car.setAvailable(true);
            carService.saveCar(car);
            return true;
        }
        return false;
    }

    /**
     * Calculates the potential daily revenue based on currently rented cars.
     * @return The sum of pricePerDay for all unavailable cars.
     */
    public double calculateCurrentDailyRevenuePotential() {
        List<Car> rentedCars = carRepository.findByAvailable(false);
        double total = 0.0;
        for (Car car : rentedCars) {
            total += car.getPricePerDay();
        }
        return total;
    }
}
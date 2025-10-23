package com.carrental.service;

import com.carrental.model.Car;
import com.carrental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    /**
     * Retrieves all cars from the database.
     * @return a list of all Car objects.
     */
    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    /**
     * Retrieves all cars that are currently available for rent.
     * @return a list of available Car objects.
     */
    public List<Car> findAvailableCars() {
        return carRepository.findByAvailable(true); // Call the repository method with true
    }

    /**
     * Saves a new or existing car to the database.
     * @param car The Car object to be saved.
     */
    public void saveCar(Car car) {
        carRepository.save(car);
    }

    /**
     * Finds a single car by its ID.
     * @param id The ID of the car.
     * @return The Car object, or null if not found.
     */
    public Car findCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    /**
     * Deletes a car from the database by its ID.
     * @param id The ID of the car to delete.
     */
    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }

    /**
     * Counts the total number of cars in the inventory.
     * @return The total car count.
     */
    public long countTotalCars() {
        return carRepository.count();
    }

    /**
     * Counts the number of cars currently rented out (not available).
     * @return The count of unavailable cars.
     */
    public long countRentedCars() {
        return carRepository.countByAvailable(false);
    }
}
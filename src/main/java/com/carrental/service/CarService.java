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
     * Saves a new or existing car to the database.
     * @param car The Car object to be saved.
     */
    public void saveCar(Car car) {
        carRepository.save(car);
    }

    /**
     * Finds a single car by its ID.
     * @param carId The ID of the car.
     * @return The Car object, or null if not found.
     */
    public Car findCarById(Integer carId) {
        // .findById() returns an Optional, so we use .orElse(null) to get the car or null
        return carRepository.findById(carId).orElse(null);
    }
}
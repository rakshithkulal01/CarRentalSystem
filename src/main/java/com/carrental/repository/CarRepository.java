package com.carrental.repository;

import com.carrental.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Import List

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    // Spring Data JPA creates this query automatically based on the method name
    long countByAvailable(boolean available);

    // Method to find all cars with a specific availability status
    List<Car> findByAvailable(boolean available);
}
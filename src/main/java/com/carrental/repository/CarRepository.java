package com.carrental.repository;

import com.carrental.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    // This method will automatically become "SELECT * FROM cars WHERE available = true"
    List<Car> findByAvailable(boolean available);
}

package com.carrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarRentalSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentalSystemApplication.class, args);
        System.out.println("Spring Boot Car Rental System has started.");
        System.out.println("You can access the web application at: http://localhost:9092");
    }
}

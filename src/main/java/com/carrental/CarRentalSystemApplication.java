package com.carrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the main entry point for the Spring Boot application.
 */
@SpringBootApplication
public class CarRentalSystemApplication {

    public static void main(String[] args) {
        // This single line launches the entire Spring Boot application.
        // It starts the embedded web server (Tomcat), scans for components,
        // and sets up the Spring application context.
        SpringApplication.run(CarRentalSystemApplication.class, args);
        
        System.out.println("Spring Boot Car Rental System has started.");
        System.out.println("You can access the web application at: http://localhost:9090");
    }
}

package com.carrental.controller;

import com.carrental.model.Car;
import com.carrental.model.Rental;
import com.carrental.model.User;
import com.carrental.service.CarService;
import com.carrental.service.RentalService;
import com.carrental.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    // --- Injected Services ---
    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    @Autowired
    private RentalService rentalService;

    // --- Dashboard and Car Listings ---

    @GetMapping("/dashboard")
    public String customerDashboard(HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        return "redirect:/customer/cars";
    }

    @GetMapping("/cars")
    public String viewAvailableCars(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // Use the method that only fetches available cars
        List<Car> availableCarList = carService.findAvailableCars();

        model.addAttribute("cars", availableCarList);
        model.addAttribute("user", loggedInUser);
        return "customer-cars";
    }

    // --- Car Rental Process ---

    @GetMapping("/rent/{carId}")
    public String showRentCarForm(@PathVariable Long carId, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        Car car = carService.findCarById(carId);

        if (car == null || !car.isAvailable()) {
            // Redirect if car not found or not available
            return "redirect:/customer/cars";
        }

        model.addAttribute("car", car);
        model.addAttribute("user", loggedInUser);
        return "rent-car";
    }

    @PostMapping("/rent/{carId}")
    public String processRental(@PathVariable Long carId,
                                @ModelAttribute("user") User userFormData,
                                @RequestParam("startDate") LocalDate startDate,
                                @RequestParam("endDate") LocalDate endDate,
                                HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // Update user details from the form
        loggedInUser.setPhone(userFormData.getPhone());
        loggedInUser.setAddress(userFormData.getAddress());
        userService.saveUser(loggedInUser);

        // Find the car again to be safe
        Car car = carService.findCarById(carId);

        if (car == null || !car.isAvailable()) {
            // Handle error if car somehow became unavailable
            return "redirect:/customer/cars";
        }

        // Create the rental record
        rentalService.createRental(loggedInUser, car, startDate, endDate);

        return "redirect:/customer/booking-success";
    }

    @GetMapping("/booking-success")
    public String showBookingSuccess(HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        return "booking-success";
    }

    // --- Rental History ---

    @GetMapping("/my-rentals")
    public String showMyRentals(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        List<Rental> rentals = rentalService.findRentalsByUser(loggedInUser);
        model.addAttribute("rentals", rentals);
        return "my-rentals";
    }
}
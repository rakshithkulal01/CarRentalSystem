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

import java.util.List;

@Controller
@RequestMapping("/customer") // All routes in this controller will start with /customer
public class CustomerController {

    // --- Injected Services (grouped together for clarity) ---
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

        List<Car> carList = carService.findAllCars();
        model.addAttribute("cars", carList);
        model.addAttribute("user", loggedInUser);
        return "customer-cars";
    }

    // --- Car Rental Process ---

    @GetMapping("/rent/{carId}")
    public String showRentCarForm(@PathVariable Integer carId, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // FIX: Removed unnecessary .intValue() conversion
        Car car = carService.findCarById(carId);

        if (car == null || !car.isAvailable()) {
            return "redirect:/customer/cars";
        }

        model.addAttribute("car", car);
        model.addAttribute("user", loggedInUser);
        return "rent-car";
    }

    @PostMapping("/rent/{carId}")
    public String processRental(@PathVariable Integer carId,
                                @ModelAttribute("user") User userFormData,
                                HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        loggedInUser.setPhone(userFormData.getPhone());
        loggedInUser.setAddress(userFormData.getAddress());
        userService.saveUser(loggedInUser);

        // FIX: Removed unnecessary .intValue() conversion
        Car car = carService.findCarById(carId);

        if (car == null || !car.isAvailable()) {
            return "redirect:/customer/cars";
        }

        rentalService.createRental(loggedInUser, car);
        return "redirect:/customer/booking-success";
    }

    @GetMapping("/booking-success")
    public String showBookingSuccess(HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        return "booking-success";
    }

    // --- NEW: Method to show user's rental history ---

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
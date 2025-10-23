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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CarService carService;

    @Autowired
    private RentalService rentalService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) {
            return "redirect:/login";
        }

        long totalUsers = userService.countTotalUsers();
        long totalCars = carService.countTotalCars();
        long rentedCars = carService.countRentedCars();
        double dailyRevenue = rentalService.calculateCurrentDailyRevenuePotential();

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalCars", totalCars);
        model.addAttribute("rentedCars", rentedCars);
        model.addAttribute("dailyRevenue", dailyRevenue);

        return "admin-dashboard";
    }

    @GetMapping("/cars")
    public String manageCars(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) {
            return "redirect:/login";
        }

        List<Car> carList = carService.findAllCars();
        model.addAttribute("cars", carList);
        model.addAttribute("newCar", new Car());
        return "admin-cars";
    }

    @PostMapping("/cars/add")
    public String addCar(@ModelAttribute("newCar") Car newCar, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) {
            return "redirect:/login";
        }

        carService.saveCar(newCar);
        return "redirect:/admin/cars";
    }

    @PostMapping("/cars/delete/{id}") // Handles the delete request
    public String deleteCar(@PathVariable Long id, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) {
            return "redirect:/login";
        }

        carService.deleteCarById(id);
        return "redirect:/admin/cars";
    }


    @GetMapping("/rentals")
    public String manageRentals(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) {
            return "redirect:/login";
        }

        List<Rental> allRentals = rentalService.findAllRentals();
        model.addAttribute("rentals", allRentals);
        return "admin-rentals";
    }

    @PostMapping("/rentals/return/{rentalId}")
    public String markRentalAsReturned(@PathVariable Long rentalId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) {
            return "redirect:/login";
        }

        rentalService.returnRental(rentalId);
        return "redirect:/admin/rentals";
    }
}
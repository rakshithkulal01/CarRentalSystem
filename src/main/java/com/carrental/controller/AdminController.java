package com.carrental.controller;

import com.carrental.model.Car;
import com.carrental.model.Rental;
import com.carrental.model.User;
import com.carrental.service.CarService;
import com.carrental.service.RentalService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin") // All routes in this controller will start with /admin
public class AdminController {

    @Autowired
    private CarService carService;

    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        // Security Check: Ensure user is logged in and is an ADMIN
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) {
            return "redirect:/login"; // Or an access-denied page
        }
        
        // For now, the admin dashboard can just be the car management page
        return "redirect:/admin/cars";
    }

    @GetMapping("/cars")
    public String manageCars(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        // Security Check
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) {
            return "redirect:/login";
        }

        List<Car> carList = carService.findAllCars();
        model.addAttribute("cars", carList);
        model.addAttribute("newCar", new Car()); // For the 'Add New Car' form

        return "admin-cars"; // Returns admin-cars.html
    }
    
    @PostMapping("/cars/add")
    public String addCar(@ModelAttribute("newCar") Car newCar, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        // Security Check
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) {
            return "redirect:/login";
        }

        carService.saveCar(newCar);
        return "redirect:/admin/cars";
    }
    // In src/main/java/com/carrental/controller/AdminController.java

// Make sure RentalService is autowired in this controller
@Autowired
private RentalService rentalService;

// ... (inside the AdminController class)

    @GetMapping("/rentals")
    public String manageRentals(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) {
            return "redirect:/login"; // Security check
        }

        List<Rental> allRentals = rentalService.findAllRentals();
        model.addAttribute("rentals", allRentals);

        return "admin-rentals"; // Returns the new admin-rentals.html page
    }
}
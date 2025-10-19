package com.carrental.controller;

import com.carrental.model.Car;
import com.carrental.model.User;
import com.carrental.service.CarService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customer") // All routes in this controller will start with /customer
public class CustomerController {

    @Autowired
    private CarService carService;

    @GetMapping("/dashboard")
    public String customerDashboard(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        // Security Check: Ensure a user is logged in
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        
        // The main customer dashboard will be the page showing available cars
        return "redirect:/customer/cars";
    }

    @GetMapping("/cars")
    public String viewAvailableCars(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        // Security Check
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        List<Car> carList = carService.findAllCars();
        model.addAttribute("cars", carList);
        model.addAttribute("user", loggedInUser); // Pass user to the view

        return "customer-cars"; // Returns customer-cars.html
    }
}
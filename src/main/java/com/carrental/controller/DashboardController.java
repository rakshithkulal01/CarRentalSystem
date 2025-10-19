
package com.carrental.controller;

import com.carrental.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        // Get the user object from the session
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        // SECURITY CHECK: If no user is in the session, redirect to login
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // If user is logged in, add their info to the model and show the dashboard
        model.addAttribute("user", loggedInUser);
        return "dashboard"; // This will look for dashboard.html
    }
}
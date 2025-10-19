// In src/main/java/com/carrental/controller/AuthController.java

package com.carrental.controller;

import com.carrental.model.User;
import com.carrental.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // This method handles GET requests for the /register URL
    // It will return the registration form page.
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    
    // This method handles the form submission from the registration page
    @PostMapping("/register")
    public String registerUser(User user, RedirectAttributes redirectAttributes) {
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
        return "redirect:/login";
    }

    // This method handles GET requests for the /login URL
    // It will return the login form page.
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Returns login.html
    }

    // This method handles the POST request from the login form submission
   // In src/main/java/com/carrental/controller/AuthController.java

// ... (other methods and imports)

@PostMapping("/login")
public String loginUser(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

    User user = userService.loginUser(email, password);

    if (user != null) {
        // Login successful, store user in session
        session.setAttribute("loggedInUser", user);

        // --- NEW: ROLE-BASED REDIRECTION ---
        if ("ADMIN".equals(user.getRole())) {
            // If the user is an ADMIN, send them to the admin dashboard
            return "redirect:/admin/dashboard";
        } else {
            // Otherwise, send them to the customer dashboard
            return "redirect:/customer/dashboard";
        }

    } else {
        // Login failed
        model.addAttribute("errorMessage", "Invalid email or password. Please try again.");
        return "login";
    }
}
    @PostMapping("/logout")
public String logout(HttpSession session) {
    // Invalidate the session, clearing all attributes (like "loggedInUser")
    session.invalidate();
    // Redirect the user to the home page or login page
    return "redirect:/login";
}

}
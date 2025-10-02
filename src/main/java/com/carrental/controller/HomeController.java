package com.carrental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // <-- Marks this class as a Spring MVC Controller
public class HomeController {

    /**
     * This method handles GET requests to the root URL ("/").
     * When a user visits http://localhost:9090/, this method will be executed.
     * @return The name of the HTML template to display ("index.html").
     */
    @GetMapping("/")
    public String showHomePage() {
        // This tells Spring to look for a file named "index.html"
        // in our templates folder and render it.
        return "index";
    }
}

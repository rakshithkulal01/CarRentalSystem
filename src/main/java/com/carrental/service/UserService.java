package com.carrental.service;

import com.carrental.model.User;
import com.carrental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional; // Import Optional

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Saves a new user or updates an existing one.
     * @param user The User object to be saved/updated.
     */
    public void saveUser(User user) {
        userRepository.save(user);
    }

    /**
     * Verifies a user's credentials for login.
     * @param email The user's email.
     * @param password The user's password.
     * @return The User object if credentials are correct, otherwise null.
     */
    public User loginUser(String email, String password) {
        // Find the user by their email address
        // FIX: Handle the Optional returned by the repository
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = optionalUser.orElse(null); // Get User or null if not found

        // Check if user exists and if the password matches
        if (user != null && user.getPassword().equals(password)) {
            return user; // Login successful
        }
        
        return null; // Login failed
    }

    /**
     * Counts the total number of registered users.
     * @return The total user count.
     */
    public long countTotalUsers() {
        return userRepository.count();
    }
}
package com.carrental.service;

import com.carrental.model.User;
import com.carrental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // Spring will automatically inject an instance of UserRepository here
    @Autowired
    private UserRepository userRepository;

    /**
     * Saves a new user to the database. This is used for registration.
     * @param user The User object to be saved.
     */
    public void saveUser(User user) {
        // Here you could add logic for password hashing before saving
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
        java.util.Optional<User> optionalUser = userRepository.findByEmail(email);

        // Check if a user with that email was found AND if the password matches
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                return user; // Login successful
            }
        }
        
        return null; // Login failed (user not found or password incorrect)
    }
}
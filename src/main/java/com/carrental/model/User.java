package com.carrental.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // <-- Tells JPA that this class is a table in the database
@Table(name = "users") // <-- Explicitly maps this class to the "users" table
public class User {

    @Id // <-- Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // <-- Configures the ID to be auto-generated
    private int id;
    
    private String name;
    private String email;
    private String password;
    private String role;

    // --- Constructors, Getters, and Setters remain the same ---
    // (No changes needed below this line)

    public User() {}

    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
    // Getters and Setters...
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

package com.carrental.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Changed to Long for better compatibility

    @Column(nullable = false)
    private String name;

    // FIX 1: Added constraints to ensure email is never null and always unique
    @Column(nullable = false, unique = true)
    private String email;

    // FIX 2: Added constraint to ensure password is never null
    @Column(nullable = false)
    private String password;

    // FIX 3: Set a default role for every new user
    private String role = "CUSTOMER";

    // --- Constructors, Getters, and Setters ---
    // (No changes needed in the methods themselves, but updated id type)

    public User() {}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    // Getters and Setters...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
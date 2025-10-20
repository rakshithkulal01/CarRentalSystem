package com.carrental.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Best practice: Use Long for IDs

    private String make;
    private String model;
    private int year;
    private double pricePerDay;
    private boolean available = true; // Cars are available by default

    // --- Constructors ---
    public Car() {}

    public Car(String make, String model, int year, double pricePerDay) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.pricePerDay = pricePerDay;
    }

    // --- Corrected Getters and Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // FIX 1: Renamed from getName/setName to getMake/setMake
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    // FIX 2: Renamed from getyear/setyear to getYear/setYear (camelCase)
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
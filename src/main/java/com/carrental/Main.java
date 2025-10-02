package com.carrental;

import com.carrental.dao.CarDao;
import com.carrental.dao.RentalDao;
import com.carrental.dao.UserDao;
import com.carrental.model.Car;
import com.carrental.model.Rental;
import com.carrental.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    // DAOs for database access
    private static final UserDao userDao = new UserDao();
    private static final CarDao carDao = new CarDao();
    private static final RentalDao rentalDao = new RentalDao();

    // Scanner for user input
    private static final Scanner scanner = new Scanner(System.in);

    // To keep track of the logged-in user
    private static User loggedInUser = null;

    public static void main(String[] args) {
        // --- Database Initialization ---
        // Create tables if they don't exist. This is essential for the first run.
        userDao.createUserTable();
        carDao.createCarTable();
        rentalDao.createRentalTable();

        System.out.println("Welcome to the Car Rental System!");

        // Main application loop
        while (true) {
            showMainMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    handleRegister();
                    break;
                case 3:
                    System.out.println("Thank you for using the Car Rental System. Goodbye!");
                    scanner.close(); // Close the scanner before exiting
                    return; // Exit the application
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Return an invalid choice
        }
    }

    private static void handleRegister() {
        System.out.println("\n--- User Registration ---");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Create a new User object, default role is 'customer'
        User newUser = new User(0, name, email, password, "customer");
        userDao.addUser(newUser);
    }

    private static void handleLogin() {
        System.out.println("\n--- User Login ---");
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        loggedInUser = userDao.getUserByEmailAndPassword(email, password);

        if (loggedInUser != null) {
            System.out.println("Login successful! Welcome, " + loggedInUser.getName() + ".");
            // Direct user to the appropriate menu based on their role
            if ("admin".equals(loggedInUser.getRole())) {
                showAdminMenu();
            } else {
                showCustomerMenu();
            }
        } else {
            System.out.println("Login failed. Invalid email or password.");
        }
    }

    private static void showAdminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add a new car");
            System.out.println("2. View all cars");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");

            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    addNewCar();
                    break;
                case 2:
                    viewAllCars();
                    break;
                case 3:
                    loggedInUser = null; // Logout the user
                    return; // Return to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addNewCar() {
        System.out.println("\n--- Add New Car ---");
        System.out.print("Enter car name (e.g., Toyota Camry): ");
        String name = scanner.nextLine();
        System.out.print("Enter car model (e.g., 2023): ");
        String model = scanner.nextLine();
        System.out.print("Enter price per day: ");
        double price = 0;
        try {
            price = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format. Please enter a number.");
            return;
        }

        Car newCar = new Car(0, name, model, price, true); // New cars are available by default
        carDao.addCar(newCar);
    }

    private static void viewAllCars() {
        List<Car> cars = carDao.getAllCars();
        System.out.println("\n--- All Cars in the System ---");
        if (cars.isEmpty()) {
            System.out.println("No cars found.");
        } else {
            // Print table header
            System.out.printf("%-5s | %-20s | %-10s | %-15s | %-12s%n", "ID", "Name", "Model", "Price/Day", "Availability");
            System.out.println("-------------------------------------------------------------------------");
            // Print each car's details
            for (Car car : cars) {
                System.out.printf("%-5d | %-20s | %-10s | $%-14.2f | %-12s%n",
                        car.getId(),
                        car.getName(),
                        car.getModel(),
                        car.getPricePerDay(),
                        car.isAvailable() ? "Available" : "Rented");
            }
        }
    }

    private static void showCustomerMenu() {
        while (true) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. View available cars");
            System.out.println("2. Book a car");
            System.out.println("3. View my rentals");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    viewAvailableCars();
                    break;
                case 2:
                    bookACar();
                    break;
                case 3:
                    viewMyRentals();
                    break;
                case 4:
                    loggedInUser = null; // Logout the user
                    return; // Return to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewAvailableCars() {
        List<Car> allCars = carDao.getAllCars();
        // Use Java Streams to filter for available cars
        List<Car> availableCars = allCars.stream()
                                         .filter(Car::isAvailable)
                                         .collect(Collectors.toList());

        System.out.println("\n--- Available Cars for Rent ---");
        if (availableCars.isEmpty()) {
            System.out.println("Sorry, no cars are available for rent at the moment.");
        } else {
            System.out.printf("%-5s | %-20s | %-10s | %-15s%n", "ID", "Name", "Model", "Price/Day");
            System.out.println("---------------------------------------------------------------");
            for (Car car : availableCars) {
                System.out.printf("%-5d | %-20s | %-10s | $%-14.2f%n",
                        car.getId(),
                        car.getName(),
                        car.getModel(),
                        car.getPricePerDay());
            }
        }
    }
    
    private static void bookACar() {
        System.out.println("\n--- Book a Car ---");
        viewAvailableCars(); // Show available cars first
        
        System.out.print("\nEnter the ID of the car you want to book: ");
        int carId = getUserChoice();
        
        System.out.print("Enter the number of days you want to rent the car: ");
        int days = getUserChoice();

        if (carId <= 0 || days <= 0) {
            System.out.println("Invalid car ID or number of days.");
            return;
        }

        // Find the selected car from the list of all cars
        Car selectedCar = carDao.getAllCars().stream()
                                .filter(car -> car.getId() == carId && car.isAvailable())
                                .findFirst()
                                .orElse(null);

        if (selectedCar != null) {
            // Car is available, proceed with booking
            double totalCost = selectedCar.getPricePerDay() * days;
            System.out.printf("The total cost for renting '%s' for %d days will be $%.2f%n", selectedCar.getName(), days, totalCost);
            System.out.print("Confirm booking? (yes/no): ");
            String confirmation = scanner.nextLine();

            if ("yes".equalsIgnoreCase(confirmation)) {
                // Create a new rental record
                Rental newRental = new Rental(0, loggedInUser.getId(), carId, LocalDate.now(), LocalDate.now().plusDays(days), totalCost);
                rentalDao.addRental(newRental);
                
                // Update car availability to false (rented)
                carDao.updateCarAvailability(carId, false);
                
                System.out.println("Car booked successfully!");
            } else {
                System.out.println("Booking cancelled.");
            }
        } else {
            System.out.println("Car not available or invalid ID.");
        }
    }
    
    private static void viewMyRentals() {
        List<Rental> myRentals = rentalDao.getRentalsByUserId(loggedInUser.getId());
        // We need car details as well, so let's get all cars to look up names
        List<Car> allCars = carDao.getAllCars();

        System.out.println("\n--- My Rentals ---");
        if (myRentals.isEmpty()) {
            System.out.println("You have no rental history.");
        } else {
            System.out.printf("%-10s | %-20s | %-12s | %-12s | %-10s%n", "Rental ID", "Car Name", "Rental Date", "Return Date", "Total Cost");
            System.out.println("---------------------------------------------------------------------------");
            for (Rental rental : myRentals) {
                // Find the car details for this rental
                Car rentedCar = allCars.stream()
                                       .filter(car -> car.getId() == rental.getCarId())
                                       .findFirst()
                                       .orElse(null);
                
                String carName = (rentedCar != null) ? rentedCar.getName() : "Unknown Car";
                
                System.out.printf("%-10d | %-20s | %-12s | %-12s | $%-9.2f%n",
                        rental.getId(),
                        carName,
                        rental.getRentalDate(),
                        rental.getReturnDate(),
                        rental.getTotalCost());
            }
        }
    }
}


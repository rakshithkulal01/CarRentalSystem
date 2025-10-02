package com.carrental.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // The DB_URL now points to a file in our project.
    // The database will be created automatically if it doesn't exist.
    private static final String DB_URL = "jdbc:sqlite:car_rental.db";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // The driver is now for SQLite
            Class.forName("org.sqlite.JDBC"); 
            
            System.out.println("Connecting to the SQLite database...");
            connection = DriverManager.getConnection(DB_URL);
            
            if (connection != null) {
                System.out.println("Database connected successfully!");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
        } 
        return connection;
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
             if (conn != null) {
                System.out.println("Connection test successful. Connection will be closed automatically.");
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

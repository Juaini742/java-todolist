package com.example.error;

import java.sql.SQLException;

public class ErrorHandler {

    public static void handleSQLException(SQLException e) {
        System.out.println("Error while executing SQL query");
        e.printStackTrace();
    }

    public static void handleNumberFormatException(NumberFormatException e) {
        System.out.println("Invalid input format");
    }

    public static void handleGeneralException(Exception e) {
        System.out.println("An error occurred");
        e.printStackTrace();
    }
}

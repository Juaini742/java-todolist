package com.example.controller;

import com.example.config.Database;
import com.example.error.ErrorHandler;
import com.example.repository.Query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Scanner;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

public class UserController {

    private Database database;
    private Query repository;
    private Scanner scanner;

    public UserController(Scanner scanner) {
        this.database = new Database();
        this.repository = new Query(getConnection());
        this.scanner = scanner;
    }

    private Connection getConnection() {
        return this.database.getConnection();
    }

    public void register() {

        System.out.println("Enter your username");
        String username = scanner.nextLine();
        System.out.println("Enter your password");
        String password = scanner.nextLine();

        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        String[] attributes = { "username", "password" };
        Object[] values = { username, hashPassword };
        int user = repository.insert("users", attributes, values);

        if (user > 0) {
            System.out.println("User added successfully!");
        } else {
            System.out.println("Failed to add user.");
        }

    }

    public int login() {
        System.out.println("Enter your username");
        String username = scanner.nextLine();
        System.out.println("Enter your password");
        String password = scanner.nextLine();

        try {
            ResultSet users = this.repository.findOne("users", " username = ? ", new Object[] { username });

            if (users.next()) {
                String hashPassword = users.getString("password");
                if (BCrypt.checkpw(password, hashPassword)) {
                    int userId = users.getInt("id");
                    UUID randomUUID = UUID.randomUUID();
                    Object[] token = { String.valueOf(randomUUID) };

                    Object[] params = { userId };
                    String[] attributes = { "token" };

                    int rowsUpdated = this.repository.update("users", "id = ?", attributes, token, params);
                    if (rowsUpdated > 0) {
                        ResultSet currentUser = this.repository.findOne("users", "id = ?", params);

                        if (currentUser.next()) {
                            System.out.println("Token updated successfully");
                            return currentUser.getInt("id");
                        } else {
                            return 0;
                        }
                    } else {
                        System.out.println("Token update failed");
                        return 0;
                    }

                }

            }
        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        }

        return 0;
    }

    public void getById(int userId) {
        Object[] params = { userId };
        ResultSet currentUser = this.repository.findOne("users", "id = ?", params);

        try {
            if (currentUser.next()) {
                System.out.println("---Here is user data---");
                System.out.println("Username: " + currentUser.getString("username"));
                System.out.println("token: " + currentUser.getString("token"));
                System.out.println("------------------------");
            }
        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        }
    }

    // void manual register() {
    // String query = "INSERT INTO users (username, password) VALUES (?, ? )";

    // try (Connection conn = database.connect()) {
    // PreparedStatement stmt = conn.prepareStatement(query);

    // stmt.setString(1, "xx");
    // stmt.setString(2, "123123");
    // int rowsExe = stmt.executeUpdate();

    // if (rowsExe > 0) {
    // System.out.println("User added successfully!");
    // } else {
    // System.out.println("Failed to add user.");
    // }

    // } catch (SQLException e) {
    // System.out.println("Error while adding data to database");

    // e.printStackTrace();
    // }
    // }
}

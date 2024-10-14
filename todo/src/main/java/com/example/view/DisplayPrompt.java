package com.example.view;

import java.util.Scanner;

import com.example.controller.TaskController;
import com.example.controller.UserController;

public class DisplayPrompt {

    private boolean isAuth = false;
    private Scanner scanner;
    private int userId;

    public DisplayPrompt(Scanner scanner) {
        this.scanner = scanner;
    }

    public void display() {
        while (true) {
            if (!isAuth) {
                displayLoginOptions();
            } else {
                displayAuthOptions();
            }
        }
    }

    private void displayLoginOptions() {
        System.out.println("Welcome to our application");
        System.out.println("Press 1 to Login");
        System.out.println("Press 2 to Register");
        System.out.println("Press x to Exit");

        String prompt = scanner.nextLine();

        switch (prompt) {
            case "1":
                userId = login();
                if (userId > 0) {
                    isAuth = true;
                }
                break;
            case "2":
                new UserController(scanner).register();
                break;
            case "x":
                System.out.println("Exiting application...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    public void displayAuthOptions() {
        System.out.println("Welcome! You are now logged in.");
        System.out.println("Press 1 to see tasks data");
        System.out.println("Press 2 to add new task");
        System.out.println("Press 3 to confirm task");
        System.out.println("Press 4 to update task by id");
        System.out.println("Press 5 to delete task by id");
        System.out.println("Press 6 to see user profile");
        System.out.println("Press 7 to Logout");
        System.out.println("Press x to Exit");

        String prompt = scanner.nextLine();

        switch (prompt) {
            case "1":
                new TaskController(scanner).showTask(userId);
                break;
            case "2":
                new TaskController(scanner).addTask(userId);
                break;
            case "3":
                new TaskController(scanner).confirmStatus(userId);
                break;
            case "4":
                new TaskController(scanner).updateTask(userId);
                break;
            case "5":
                new TaskController(scanner).deleteTask(userId);
                break;
            case "6":
                new UserController(scanner).getById(userId);
                break;
            case "7":
                isAuth = false;
                System.out.println("You have logged out.");
                break;
            case "x":
                System.out.println("Exiting application...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    public int login() {
        UserController userController = new UserController(scanner);
        return userController.login();
    }

}

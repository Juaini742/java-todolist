package com.example.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.example.config.Database;
import com.example.error.ErrorHandler;
import com.example.model.Task;
import com.example.repository.Query;

public class TaskController {

    private Database database;
    private Query repository;
    private Scanner scanner;

    public TaskController(Scanner scanner) {
        this.database = new Database();
        this.repository = new Query(getConnection());
        this.scanner = scanner;
    }

    private Connection getConnection() {
        return this.database.getConnection();
    }

    public void addTask(int userId) {
        Task task = createTaskFromUserInput();

        String[] attributes = { "title", "description", "userId", "status", "taskStart", "taskEnd" };
        Object[] values = { task.getTitle(), task.getDescription(), userId, false, task.getTaskStart(),
                task.getTaskEnd() };

        int result = this.repository.insert("tasks", attributes, values);
        if (result > 0) {
            System.out.println("\nAdding new task successfully\n");
        } else {
            ErrorHandler.handleSQLException(new SQLException("Adding new task failed"));
        }
    }

    public void showTask(int userId) {
        Object[] param = { userId };
        ResultSet tasks = this.repository.findAll("tasks", "userId = ?", param);

        try {
            System.out.println("\n--- Here is tasks data  ---");
            while (tasks.next()) {
                System.out.println("ID: " + tasks.getInt("id"));
                System.out.println("Title: " + tasks.getString("title"));
                System.out.println("Description: " + tasks.getString("description"));
                System.out.println("User ID: " + tasks.getString("userId"));
                System.out.println("Status: " + (tasks.getInt("status") == 0 ? "Proses" : "Done"));
                System.out.println("Start Date: " + tasks.getString("taskStart"));
                System.out.println("End Date: " + tasks.getString("taskEnd"));
                System.out.println("------------------------\n");
            }
            System.out.println("----- END -----");
        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        }
    }

    public void updateTask(int userId) {
        System.out.println("Enter id of the task");
        String taskId = scanner.nextLine();

        try {
            ResultSet task = this.repository.findOne("tasks", "id = ?", new Object[] { taskId });

            if (task.next()) {

                Task taskInput = new Task();
                System.out.println("Enter task title: " + "-" + task.getString("title") + "-");
                taskInput.setTitle(scanner.nextLine());

                System.out.println("Enter your description: " + "-" + task.getString("description") + "-");
                taskInput.setDescription(scanner.nextLine());

                System.out.println("Enter your start date: " + "-" + task.getString("taskStart") + "-");
                taskInput.setTaskStart(scanner.nextLine());

                System.out.println("Enter your end date: " + "-" + task.getString("taskEnd") + "-");
                taskInput.setTaskEnd(scanner.nextLine());

                int id = Integer.parseInt(taskId);
                String[] attributes = { "title", "description", "taskStart", "taskEnd" };

                Object[] values = {
                        taskInput.getTitle().isEmpty() ? task.getString("title") : taskInput.getTitle(),
                        taskInput.getDescription().isEmpty() ? task.getString("description")
                                : taskInput.getDescription(),
                        taskInput.getTaskStart().isEmpty() ? task.getString("taskStart") : taskInput.getTaskStart(),
                        taskInput.getTaskEnd().isEmpty() ? task.getString("taskEnd") : taskInput.getTaskEnd()
                };
                int result = this.repository.update("tasks", "id = ? AND userId = ?", attributes, values,
                        new Object[] { id, userId });
                if (result > 0) {
                    System.out.println("Update task successfully");
                } else {
                    System.out.println("Update task failed");
                }
            } else {
                System.out.println("Task not found");
            }
        } catch (NumberFormatException e) {
            ErrorHandler.handleNumberFormatException(e);
        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        }
    }

    public void confirmStatus(int userId) {
        try {
            System.out.println("Enter id of the task");
            String taskId = scanner.nextLine();
            System.out.println(
                    "Please confirm your task\n" +
                            "Type 'yes' for down\n" +
                            "Type 'no' for process");
            String status = scanner.nextLine();
            System.out.println(status);
            int id = Integer.parseInt(taskId);
            String[] attributes = { "status" };
            Object[] values = { status.toUpperCase().equals("YES") };
            int result = this.repository.update("tasks", "id = ? AND userId = ?", attributes, values,
                    new Object[] { id, userId });
            if (result > 0) {
                System.out.println("Update task status successfully");
            } else {
                System.out.println("Update task status failed");
            }
        } catch (NumberFormatException e) {
            ErrorHandler.handleNumberFormatException(e);
        }
    }

    public void deleteTask(int userId) {
        System.out.println("Enter id of the task");
        String taskId = scanner.nextLine();

        try {
            int id = Integer.parseInt(taskId);
            int result = this.repository.delete("tasks", "id = ? AND userId = ?", new Object[] { id, userId });
            if (result > 0) {
                System.out.println("Delete task successfully");
            } else {
                System.out.println("Delete task failed");
            }
        } catch (NumberFormatException e) {
            ErrorHandler.handleNumberFormatException(e);
        }

    }

    private Task createTaskFromUserInput() {
        Task task = new Task();
        System.out.println("Enter task title");
        task.setTitle(scanner.nextLine());

        System.out.println("Enter your description");
        task.setDescription(scanner.nextLine());

        System.out.println("Enter your start date");
        task.setTaskStart(scanner.nextLine());

        System.out.println("Enter your end date");
        task.setTaskEnd(scanner.nextLine());

        return task;
    }

}

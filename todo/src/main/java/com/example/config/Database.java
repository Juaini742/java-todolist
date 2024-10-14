package com.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.Environment;

public class Database {

    private static final String url = "jdbc:mysql://localhost:3306/test_java";
    private static final String username = new String(new Environment().DB_USERNAME);
    private static final String password = new String(new Environment().DB_PASSWORD);
    private Connection connection;

    public Database() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database", e);
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error closing database connection ", e);
        }
    }

    public PreparedStatement prepareStatement(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error preparing statement", e);
        }
    }

    public ResultSet executeQuery(PreparedStatement stmt) {
        try {
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing query", e);
        }
    }
}

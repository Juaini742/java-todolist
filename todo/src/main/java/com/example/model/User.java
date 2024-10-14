package com.example.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String token;
    private boolean status;
    private String taskStart;
    private String taskEnd;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTaskStart(String taskStart) {
        this.taskStart = taskStart;
    }

    public void setTaskEnd(String taskEnd) {
        this.taskEnd = taskEnd;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public boolean getStatus() {
        return status;
    }

    public String getTaskStart() {
        return taskStart;
    }

    public String getTaskEnd() {
        return taskEnd;
    }
}

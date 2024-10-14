package com.example.model;

public class Task {
    private String title;
    private String description;
    private int userId;
    private boolean status;
    private String taskStart;
    private String taskEnd;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setTaskStart(String taskStart) {
        this.taskStart = taskStart;
    }

    public void setTaskEnd(String taskEnd) {
        this.taskEnd = taskEnd;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getUserId() {
        return userId;
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

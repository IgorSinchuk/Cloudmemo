package com.nonexistentware.igorsinchuk.cloudmemo.model;

public class TaskModel {
    private String id;
    private String userName;
    private String title;
    private String description;
    private String taskTime;

    public TaskModel(String title, String description) {
    }

    public TaskModel(String id, String userName, String title, String description, String taskTime) {
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.description = description;
        this.taskTime = taskTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }
}


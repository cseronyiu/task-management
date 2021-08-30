package com.zaman.taskmanagement.Request;

import java.util.Date;

public class CreateTaskRequest {
    private Integer id;
    private String description;
    private String status;
    private Integer projectId;
    private Date dueDate;

    public CreateTaskRequest() {
    }

    public CreateTaskRequest(Integer id, String description, String status, Integer projectId, Date dueDate) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.projectId = projectId;
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}

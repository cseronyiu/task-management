package com.zaman.taskmanagement.service;

import com.zaman.taskmanagement.Request.CreateTaskRequest;
import com.zaman.taskmanagement.Response.BaseResponse;
import com.zaman.taskmanagement.entity.User;

public interface TaskService {

    BaseResponse createTask(CreateTaskRequest request, User user);

    BaseResponse editTask(CreateTaskRequest request, User user);

    BaseResponse getTask(Integer taskId, User user);

    BaseResponse getAllByProject(Integer projectId, User user);

    BaseResponse getExpiredTasks(User user);

    BaseResponse getTaskByStatus(String status,User user);

    BaseResponse getTasksByUser(String userId);

    BaseResponse getTaskList(User user);
}

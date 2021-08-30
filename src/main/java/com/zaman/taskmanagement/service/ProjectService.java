package com.zaman.taskmanagement.service;

import com.zaman.taskmanagement.Request.CreateProjectRequest;
import com.zaman.taskmanagement.Response.BaseResponse;
import com.zaman.taskmanagement.entity.User;

public interface ProjectService {

    BaseResponse createProject(CreateProjectRequest project, User user);

    BaseResponse getAllProjects(User user);

    BaseResponse deleteProject(Integer projectId, User user);

    BaseResponse getProjectsByUser(String username);
}


package com.zaman.taskmanagement.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaman.taskmanagement.Request.CreateProjectRequest;
import com.zaman.taskmanagement.Response.BaseResponse;
import com.zaman.taskmanagement.Response.ProjectResponseModel;
import com.zaman.taskmanagement.entity.Project;
import com.zaman.taskmanagement.entity.Task;
import com.zaman.taskmanagement.entity.User;
import com.zaman.taskmanagement.repository.ProjectRepository;
import com.zaman.taskmanagement.repository.TaskRepository;
import com.zaman.taskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public BaseResponse createProject(CreateProjectRequest project, User user) {
        BaseResponse baseResponse = new BaseResponse();
        if (project.getName() != null) {
            Project savedProject = projectRepository.findFirstByNameAndDeletedFalse(project.getName());
            if (savedProject == null) {
                savedProject = new Project();

                savedProject.setName(project.getName());
                savedProject.setProjectOwner(user.getId());
                savedProject.setCreatedBy(user.getId());
                projectRepository.save(savedProject);
                baseResponse.setData(true);
                baseResponse.setStatus(HttpStatus.OK);

            } else {
                baseResponse.setData(false);
                baseResponse.setStatus(HttpStatus.CONFLICT);
                baseResponse.setErrorMessage("Project Name Already exist");
            }
        } else {
            baseResponse.setData(false);
            baseResponse.setStatus(HttpStatus.BAD_REQUEST);
            baseResponse.setErrorMessage("Bad request");
        }

        return baseResponse;
    }

    @Override
    public BaseResponse getAllProjects(User user) {
        BaseResponse baseResponse = new BaseResponse();
        List<Project> projects = projectRepository.findAllByProjectOwnerAndDeletedFalse(user.getId());
        List<ProjectResponseModel> projectResponseModels = new ArrayList<>();
        if (projects != null) {
            for (Project project :
                    projects) {
                ProjectResponseModel projectResponseModel = new ProjectResponseModel();
                projectResponseModel.setId(project.getId());
                projectResponseModel.setName(project.getName());
                projectResponseModels.add(projectResponseModel);
            }
            baseResponse.setData(projectResponseModels);
            baseResponse.setStatus(HttpStatus.OK);
        } else {
            baseResponse.setData(false);
            baseResponse.setStatus(HttpStatus.NO_CONTENT);
            baseResponse.setErrorMessage("No Project found.");
        }

        return baseResponse;
    }

    @Override
    public BaseResponse deleteProject(Integer projectId, User user) {
        BaseResponse baseResponse = new BaseResponse();
        if (projectId > 0) {
            Project project = projectRepository.findFirstByIdAndProjectOwnerAndDeletedFalse(projectId, user.getId());
            if (project != null) {
                deleteTasks(project.getId());
                project.setDeleted(true);
                projectRepository.save(project);
                baseResponse.setData(true);
                baseResponse.setStatus(HttpStatus.OK);
            } else {
                baseResponse.setData(false);
                baseResponse.setStatus(HttpStatus.NO_CONTENT);
                baseResponse.setErrorMessage("No Project found.");
            }
        } else {
            baseResponse.setData(false);
            baseResponse.setStatus(HttpStatus.BAD_REQUEST);
            baseResponse.setErrorMessage("Bad request");
        }

        return baseResponse;
    }

    @Override
    public BaseResponse getProjectsByUser(String userName) {
        BaseResponse baseResponse = new BaseResponse();
        User user = userRepository.findFirstByUsername(userName);
        if (user == null) {
            baseResponse.setData(false);
            baseResponse.setStatus(HttpStatus.BAD_REQUEST);
            baseResponse.setErrorMessage("Bad request");
            return baseResponse;
        }
        List<Project> projects = projectRepository.findAllByProjectOwnerAndDeletedFalse(user.getId());
        List<ProjectResponseModel> projectResponseModels = new ArrayList<>();
        if (projects != null) {
            for (Project project :
                    projects) {
                ProjectResponseModel projectResponseModel = new ProjectResponseModel();
                projectResponseModel.setId(project.getId());
                projectResponseModel.setName(project.getName());
                projectResponseModels.add(projectResponseModel);
            }

            baseResponse.setData(projectResponseModels);
            baseResponse.setStatus(HttpStatus.OK);
        } else {
            baseResponse.setData(false);
            baseResponse.setStatus(HttpStatus.NO_CONTENT);
            baseResponse.setErrorMessage("No Project found.");
        }

        return baseResponse;
    }

    private void deleteTasks(Integer id) {
        List<Task> tasks = taskRepository.findAllByProject_IdAndDeletedFalse(id);
        for (Task task : tasks) {
            task.setDeleted(true);
        }
        taskRepository.saveAll(tasks);
    }

}

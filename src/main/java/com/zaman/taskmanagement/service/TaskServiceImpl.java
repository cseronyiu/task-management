package com.zaman.taskmanagement.service;

import com.zaman.taskmanagement.Request.CreateTaskRequest;
import com.zaman.taskmanagement.Response.BaseResponse;
import com.zaman.taskmanagement.Response.GetTaskResponse;
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
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public BaseResponse createTask(CreateTaskRequest request, User user) {
        BaseResponse baseResponse = new BaseResponse();
        BaseResponse validateRequest = validateTask(request);
        if (validateRequest == null) {
            Project project = projectRepository.findFirstByIdAndProjectOwnerAndDeletedFalse(request.getProjectId(),user.getId());
            if (project == null) {
                baseResponse.setData(false);
                baseResponse.setStatus(HttpStatus.BAD_REQUEST);
                baseResponse.setErrorMessage("Bad request");
                return baseResponse;
            }
            Task task = taskRepository.findFirstByProject_IdAndDescriptionAndDeletedFalse(request.getProjectId(), request.getDescription());
            if (task == null) {
                task = new Task();
                baseResponse = saveOrUpdateTask(task, request, user, project, true);
            } else {
                baseResponse.setData(false);
                baseResponse.setStatus(HttpStatus.CONFLICT);
                baseResponse.setErrorMessage("Task Already exist");
            }
        } else {
            baseResponse.setData(false);
            baseResponse.setStatus(HttpStatus.BAD_REQUEST);
            baseResponse.setErrorMessage("Bad request");
        }

        return baseResponse;
    }

    @Override
    public BaseResponse editTask(CreateTaskRequest request, User user) {
        BaseResponse baseResponse = new BaseResponse();
        BaseResponse validateRequest = validateTask(request);
        if (validateRequest == null) {
            Project project = projectRepository.findFirstByIdAndProjectOwnerAndDeletedFalse(request.getProjectId(),user.getId());
            if (project == null) {
                baseResponse.setData(false);
                baseResponse.setStatus(HttpStatus.BAD_REQUEST);
                baseResponse.setErrorMessage("Bad request");
                return baseResponse;
            }
            Task task = taskRepository.findFirstByIdAndDeletedFalse(request.getId());
            if (task != null) {
                if (task.getDescription() != request.getDescription()) {
                    Task dbTask = taskRepository.findFirstByProject_IdAndDescriptionAndDeletedFalse(request.getProjectId(), request.getDescription());
                    if (dbTask == null) {
                        baseResponse = saveOrUpdateTask(task, request, user, project, false);
                    } else {
                        baseResponse.setData(false);
                        baseResponse.setStatus(HttpStatus.CONFLICT);
                        baseResponse.setErrorMessage("Task Already exist");
                    }
                } else {
                    baseResponse = saveOrUpdateTask(task, request, user, project, false);
                }
            } else {
                baseResponse.setData(false);
                baseResponse.setStatus(HttpStatus.BAD_REQUEST);
                baseResponse.setErrorMessage("Bad request");
            }

        } else {
            baseResponse = validateRequest;
        }
        return baseResponse;
    }

    @Override
    public BaseResponse getTask(Integer taskId, User user) {
        BaseResponse baseResponse = new BaseResponse();
        if (taskId > 0) {
            Task task = taskRepository.findFirstByIdAndDeletedFalse(taskId);
            if (task != null) {
                if (task.getProject().getProjectOwner() == user.getId()) {
                    GetTaskResponse taskResponse = new GetTaskResponse();
                    taskResponse.setId(task.getId());
                    taskResponse.setDescription(task.getDescription());
                    taskResponse.setDueDate(task.getDueDate());
                    taskResponse.setStatus(task.getStatus());
                    taskResponse.setProject(task.getProject().getName());

                    baseResponse.setData(taskResponse);
                    baseResponse.setStatus(HttpStatus.OK);
                } else {
                    baseResponse.setData(false);
                    baseResponse.setStatus(HttpStatus.BAD_REQUEST);
                    baseResponse.setErrorMessage("Bad request");
                }

            } else {
                baseResponse.setData(false);
                baseResponse.setStatus(HttpStatus.BAD_REQUEST);
                baseResponse.setErrorMessage("Bad request");
            }
        } else {
            baseResponse.setData(false);
            baseResponse.setStatus(HttpStatus.BAD_REQUEST);
            baseResponse.setErrorMessage("Bad request");
        }
        return baseResponse;
    }


    @Override
    public BaseResponse getAllByProject(Integer projectId, User user) {
        BaseResponse baseResponse = new BaseResponse();
        if (projectId > 0) {
            Project project = projectRepository.findFirstByIdAndDeletedFalse(projectId);

            if (project != null) {
                if (project.getProjectOwner() == user.getId()) {
                    List<Task> tasks = taskRepository.findAllByProject_IdAndDeletedFalse(projectId);
                    if (tasks != null) {
                        List<GetTaskResponse> taskResponses = mapTaskList(tasks);
                        baseResponse.setStatus(HttpStatus.OK);
                        baseResponse.setData(taskResponses);
                    } else {
                        baseResponse.setStatus(HttpStatus.OK);
                        baseResponse.setErrorMessage("No task found");
                    }
                } else {
                    baseResponse.setStatus(HttpStatus.BAD_REQUEST);
                    baseResponse.setErrorMessage("Bad request");
                }
            }
        }
        return baseResponse;
    }


    @Override
    public BaseResponse getExpiredTasks(User user) {
        BaseResponse baseResponse = new BaseResponse();
        List<Project> projects = projectRepository.findAllByProjectOwnerAndDeletedFalse(user.getId());
        List<Integer> projectIds = new ArrayList<>();
        for (Project project : projects) {
            projectIds.add(project.getId());
        }
        if (!projectIds.isEmpty()) {
            List<Task> tasks = taskRepository.findAllByProject_IdInAndDueDateBeforeAndDeletedFalse(projectIds, new Date());
            List<GetTaskResponse> taskResponses = mapTaskList(tasks);
            baseResponse.setStatus(HttpStatus.OK);
            baseResponse.setData(taskResponses);
        } else {
            baseResponse.setStatus(HttpStatus.NO_CONTENT);
            baseResponse.setErrorMessage("No task found");
        }

        return baseResponse;
    }


    @Override
    public BaseResponse getTaskByStatus(String status, User user) {
        BaseResponse baseResponse = new BaseResponse();
        List<Project> projects = projectRepository.findAllByProjectOwnerAndDeletedFalse(user.getId());
        List<Integer> projectIds = new ArrayList<>();
        for (Project project : projects) {
            projectIds.add(project.getId());
        }
        if (!projectIds.isEmpty()) {
            List<Task> tasks = taskRepository.findAllByProject_IdInAndStatusAndDeletedFalse(projectIds, status);
            List<GetTaskResponse> taskResponses = mapTaskList(tasks);
            baseResponse.setStatus(HttpStatus.OK);
            baseResponse.setData(taskResponses);
        } else {
            baseResponse.setStatus(HttpStatus.NO_CONTENT);
            baseResponse.setErrorMessage("No task found");
        }

        return baseResponse;
    }

    @Override
    public BaseResponse getTasksByUser(String userName) {
        BaseResponse baseResponse = new BaseResponse();
        User user = userRepository.findFirstByUsername(userName);
        List<Project> projects = projectRepository.findAllByProjectOwnerAndDeletedFalse(user.getId());
        List<Integer> projectIds = new ArrayList<>();
        for (Project project : projects) {
            projectIds.add(project.getId());
        }
        if (!projectIds.isEmpty()) {
            List<Task> tasks = taskRepository.findAllByProject_IdInAndDeletedFalse(projectIds);
            List<GetTaskResponse> taskResponses = mapTaskList(tasks);
            baseResponse.setStatus(HttpStatus.OK);
            baseResponse.setData(taskResponses);
        } else {
            baseResponse.setStatus(HttpStatus.NO_CONTENT);
            baseResponse.setErrorMessage("No task found");
        }

        return baseResponse;
    }

    @Override
    public BaseResponse getTasksByProject(Integer projectId) {
        BaseResponse baseResponse = new BaseResponse();
        Project project = projectRepository.findFirstByIdAndDeletedFalse(projectId);
        if (project != null) {
            List<Task> tasks = taskRepository.findAllByProject_IdAndDeletedFalse(project.getId());
            List<GetTaskResponse> taskResponses = mapTaskList(tasks);
            baseResponse.setStatus(HttpStatus.OK);
            baseResponse.setData(taskResponses);
        } else {
            baseResponse.setStatus(HttpStatus.NO_CONTENT);
            baseResponse.setErrorMessage("No task found");
        }

        return baseResponse;
    }

    @Override
    public BaseResponse getTaskList(User user) {
        BaseResponse baseResponse = new BaseResponse();
        List<Project> projects = projectRepository.findAllByProjectOwnerAndDeletedFalse(user.getId());
        List<Integer> projectIds = new ArrayList<>();
        for (Project project : projects) {
            projectIds.add(project.getId());
        }
        if (!projectIds.isEmpty()) {
            List<Task> tasks = taskRepository.findAllByProject_IdInAndDeletedFalse(projectIds);
            List<GetTaskResponse> taskResponses = mapTaskList(tasks);
            baseResponse.setStatus(HttpStatus.OK);
            baseResponse.setData(taskResponses);
        } else {
            baseResponse.setStatus(HttpStatus.NO_CONTENT);
            baseResponse.setErrorMessage("No task found");
        }

        return baseResponse;
    }

    private List<GetTaskResponse> mapTaskList(List<Task> tasks) {
        List<GetTaskResponse> taskResponses = new ArrayList<>();
        for (Task task : tasks) {
            GetTaskResponse taskResponse = new GetTaskResponse();
            taskResponse.setId(task.getId());
            taskResponse.setDescription(task.getDescription());
            taskResponse.setStatus(task.getStatus());
            taskResponse.setDueDate(task.getDueDate());
            taskResponse.setProject(task.getProject().getName());
            taskResponses.add(taskResponse);
        }
        return taskResponses;
    }

    private BaseResponse saveOrUpdateTask(Task task, CreateTaskRequest request, User user, Project project, boolean isSave) {
        BaseResponse baseResponse = new BaseResponse();
        if (isSave == true) {
            task.setCreatedBy(user.getId());
        } else {
            task.setUpdatedBy(user.getId());
        }
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());
        task.setProject(project);
        taskRepository.save(task);
        baseResponse.setData(true);
        baseResponse.setStatus(HttpStatus.OK);
        return baseResponse;
    }

    private BaseResponse validateTask(CreateTaskRequest request) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(false);
        baseResponse.setStatus(HttpStatus.BAD_REQUEST);
        if (request.getProjectId() <= 0) {
            baseResponse.setErrorMessage("Invalid Project.");
            return baseResponse;
        } else if (request.getDescription() == null) {
            baseResponse.setErrorMessage("Description is empty.");
            return baseResponse;
        } else if (request.getStatus() == null) {
            baseResponse.setErrorMessage("Status is empty.");
            return baseResponse;
        } else if (request.getDueDate() == null) {
            baseResponse.setErrorMessage("Due Date is empty.");
            return baseResponse;
        } else {
            return null;
        }
    }
}

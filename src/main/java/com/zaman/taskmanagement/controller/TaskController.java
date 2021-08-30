package com.zaman.taskmanagement.controller;

import com.zaman.taskmanagement.Request.CreateTaskRequest;
import com.zaman.taskmanagement.Response.BaseResponse;
import com.zaman.taskmanagement.entity.User;
import com.zaman.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TaskController {
    @Autowired
    private TaskService taskService;


    @RequestMapping(path = "/createTask", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('CREATE_TASK')")
    public ResponseEntity<?> createTask(@RequestBody CreateTaskRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        BaseResponse baseResponse = taskService.createTask(request, user);
        return new ResponseEntity<BaseResponse>(baseResponse, baseResponse.getStatus());
    }

    @RequestMapping(path = "/editTask", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('EDIT_TASK')")
    public ResponseEntity<?> editTask(@RequestBody CreateTaskRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        BaseResponse baseResponse = taskService.editTask(request, user);
        return new ResponseEntity<BaseResponse>(baseResponse, baseResponse.getStatus());
    }


    @RequestMapping(path = "/getTask/{taskId}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('GET_TASK')")
    public ResponseEntity<?> getTask(@PathVariable Integer taskId,@AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        BaseResponse baseResponse = taskService.getTask(taskId,user);
        return new ResponseEntity<BaseResponse>(baseResponse, baseResponse.getStatus());
    }

    @RequestMapping(path = "/getTaskList", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('GET_TASK_LIST')")
    public ResponseEntity<?> getTaskList(@AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        BaseResponse baseResponse = taskService.getTaskList(user);
        return new ResponseEntity<BaseResponse>(baseResponse, baseResponse.getStatus());
    }

    @RequestMapping(path = "/getAllByProject/{projectId}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('TASKS_BY_PROJECT')")
    public ResponseEntity<?> getAllByProject(@PathVariable Integer projectId,@AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        BaseResponse baseResponse = taskService.getAllByProject(projectId,user);
        return new ResponseEntity<BaseResponse>(baseResponse, baseResponse.getStatus());
    }

    @RequestMapping(path = "/getExpiredTasks", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('EXPIRED_TASKS')")
    public ResponseEntity<?> getExpiredTasks(@AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        BaseResponse baseResponse = taskService.getExpiredTasks(user);
        return new ResponseEntity<BaseResponse>(baseResponse, baseResponse.getStatus());
    }

    @RequestMapping(path = "/getTaskByStatus", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('TASKS_BY_STATUS')")
    public ResponseEntity<?> getTaskByStatus(@RequestParam("status") String status ,@AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        BaseResponse baseResponse = taskService.getTaskByStatus(status,user);
        return new ResponseEntity<BaseResponse>(baseResponse, baseResponse.getStatus());
    }

    @RequestMapping(path = "/getTasksByUser", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ALL_TASKS_BY_USER')")
    public ResponseEntity<?> getTasksByUser(@RequestParam("username") String username ,@AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        BaseResponse baseResponse = taskService.getTasksByUser(username);
        return new ResponseEntity<BaseResponse>(baseResponse, baseResponse.getStatus());
    }


    @RequestMapping(path = "/getTasksByProject", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ALL_TASKS_BY_PROJECT')")
    public ResponseEntity<?> getTasksByProject(@RequestParam("projectId") Integer projectId ,@AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        BaseResponse baseResponse = taskService.getTasksByProject(projectId);
        return new ResponseEntity<BaseResponse>(baseResponse, baseResponse.getStatus());
    }



}

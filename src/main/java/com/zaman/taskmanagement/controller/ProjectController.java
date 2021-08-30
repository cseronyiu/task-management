package com.zaman.taskmanagement.controller;

import com.zaman.taskmanagement.Request.CreateProjectRequest;
import com.zaman.taskmanagement.Response.BaseResponse;
import com.zaman.taskmanagement.entity.Project;
import com.zaman.taskmanagement.entity.User;
import com.zaman.taskmanagement.repository.ProjectRepository;
import com.zaman.taskmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")

public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @RequestMapping(path = "/createProject", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('CREATE_PROJECT')")
    public ResponseEntity<?> createProject(@RequestBody CreateProjectRequest project, @AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        BaseResponse baseResponse = projectService.createProject(project, user);
        return new ResponseEntity<BaseResponse>(baseResponse, baseResponse.getStatus());
    }

    @RequestMapping(path = "/getAllProject", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('GET_ALL_PROJECT')")
    public ResponseEntity<?> getAllProjects(@AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        BaseResponse baseResponse = projectService.getAllProjects(user);
        return new ResponseEntity<BaseResponse>(baseResponse, baseResponse.getStatus());
    }

    @RequestMapping(path = "/deleteProject/{projectId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('DELETE_PROJECT')")
    public ResponseEntity<?> deleteProject(@PathVariable Integer projectId, @AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        BaseResponse baseResponse = projectService.deleteProject(projectId, user);
        return new ResponseEntity<BaseResponse>(baseResponse, baseResponse.getStatus());
    }


    @RequestMapping(path = "/getProjectsByUser", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ALL_PROJECTS_BY_USER')")
    public ResponseEntity<?> getTasksByUser(@RequestParam("username") String username ,@AuthenticationPrincipal UserDetails userDetails) {
        BaseResponse baseResponse = projectService.getProjectsByUser(username);
        return new ResponseEntity<BaseResponse>(baseResponse, baseResponse.getStatus());
    }

}

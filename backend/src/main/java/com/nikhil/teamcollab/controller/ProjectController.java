package com.nikhil.teamcollab.controller;

import com.nikhil.teamcollab.dto.ProjectRequest;
import com.nikhil.teamcollab.entity.Project;
import com.nikhil.teamcollab.repository.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping
    public Project createProject(@RequestBody ProjectRequest request) {
        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .workspaceId(request.getWorkspaceId())
                .createdAt(LocalDateTime.now())
                .build();

        return projectRepository.save(project);
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("/workspace/{workspaceId}")
    public List<Project> getProjectsByWorkspace(@PathVariable Long workspaceId) {
        return projectRepository.findByWorkspaceId(workspaceId);
    }
}

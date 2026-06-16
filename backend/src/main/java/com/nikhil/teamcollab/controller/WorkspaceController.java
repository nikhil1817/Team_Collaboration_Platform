package com.nikhil.teamcollab.controller;

import com.nikhil.teamcollab.dto.WorkspaceRequest;
import com.nikhil.teamcollab.entity.Workspace;
import com.nikhil.teamcollab.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @PostMapping
    public Workspace createWorkspace(@RequestBody WorkspaceRequest request) {
        Workspace workspace = Workspace.builder()
                .name(request.getName())
                .description(request.getDescription())
                .ownerId(request.getOwnerId())
                .createdAt(LocalDateTime.now())
                .build();

        return workspaceRepository.save(workspace);
    }

    @GetMapping
    public List<Workspace> getAllWorkspaces() {
        return workspaceRepository.findAll();
    }

    @GetMapping("/owner/{ownerId}")
    public List<Workspace> getWorkspacesByOwner(@PathVariable Long ownerId) {
        return workspaceRepository.findByOwnerId(ownerId);
    }
}

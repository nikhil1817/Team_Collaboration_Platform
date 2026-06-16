package com.nikhil.teamcollab.dto;

public class ProjectRequest {

    private String name;
    private String description;
    private Long workspaceId;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getWorkspaceId() {
        return workspaceId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWorkspaceId(Long workspaceId) {
        this.workspaceId = workspaceId;
    }
}

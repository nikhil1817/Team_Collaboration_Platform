package com.nikhil.teamcollab.controller;

import com.nikhil.teamcollab.dto.TaskRequest;
import com.nikhil.teamcollab.entity.Task;
import com.nikhil.teamcollab.repository.TaskRepository;
import com.nikhil.teamcollab.service.ActivityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ActivityService activityService;

    @PostMapping
    public Task createTask(@RequestBody TaskRequest request) {

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : "TODO")
                .priority(request.getPriority() != null ? request.getPriority() : "MEDIUM")
                .projectId(request.getProjectId())
                .assigneeId(request.getAssigneeId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Task savedTask = taskRepository.save(task);

        activityService.addActivity(
                "Task created: " + savedTask.getTitle()
        );

        return savedTask;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/project/{projectId}")
    public List<Task> getTasksByProject(@PathVariable Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    @GetMapping("/assignee/{assigneeId}")
    public List<Task> getTasksByAssignee(@PathVariable Long assigneeId) {
        return taskRepository.findByAssigneeId(assigneeId);
    }

    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable String status) {
        return taskRepository.findByStatus(status);
    }

    @PutMapping("/{id}")
    public Task updateTask(
            @PathVariable Long id,
            @RequestBody TaskRequest request
    ) {

        Optional<Task> optionalTask =
                taskRepository.findById(id);

        if (optionalTask.isEmpty()) {
            throw new RuntimeException(
                    "Task not found with id: " + id
            );
        }

        Task task = optionalTask.get();

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }

        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }

        if (request.getAssigneeId() != null) {
            task.setAssigneeId(request.getAssigneeId());
        }

        task.setUpdatedAt(LocalDateTime.now());

        Task updatedTask = taskRepository.save(task);

        activityService.addActivity(
                "Task updated: " + updatedTask.getTitle()
        );

        return updatedTask;
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {

        if (!taskRepository.existsById(id)) {
            return "Task not found";
        }

        taskRepository.deleteById(id);

        activityService.addActivity(
                "Task deleted with id: " + id
        );

        return "Task deleted successfully";
    }
}

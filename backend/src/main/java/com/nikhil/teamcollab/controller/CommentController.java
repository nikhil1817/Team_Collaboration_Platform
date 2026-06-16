package com.nikhil.teamcollab.controller;

import com.nikhil.teamcollab.dto.CommentRequest;
import com.nikhil.teamcollab.dto.NotificationMessage;
import com.nikhil.teamcollab.entity.Comment;
import com.nikhil.teamcollab.repository.CommentRepository;
import com.nikhil.teamcollab.service.ActivityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ActivityService activityService;

    @PostMapping
    public Comment createComment(
            @RequestBody CommentRequest request
    ) {

        Comment comment = Comment.builder()
                .taskId(request.getTaskId())
                .userId(request.getUserId())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        Comment savedComment =
                commentRepository.save(comment);

        activityService.addActivity(
                "Comment added to task: "
                        + request.getTaskId()
        );

        messagingTemplate.convertAndSend(
                "/topic/updates",
                new NotificationMessage(
                        "New comment added to task "
                                + request.getTaskId()
                )
        );

        return savedComment;
    }

    @GetMapping("/task/{taskId}")
    public List<Comment> getCommentsByTask(
            @PathVariable Long taskId
    ) {
        return commentRepository.findByTaskId(taskId);
    }

    @DeleteMapping("/{id}")
    public String deleteComment(
            @PathVariable Long id
    ) {

        if (!commentRepository.existsById(id)) {
            return "Comment not found";
        }

        commentRepository.deleteById(id);

        activityService.addActivity(
                "Comment deleted with id: " + id
        );

        messagingTemplate.convertAndSend(
                "/topic/updates",
                new NotificationMessage(
                        "Comment deleted"
                )
        );

        return "Comment deleted successfully";
    }
}

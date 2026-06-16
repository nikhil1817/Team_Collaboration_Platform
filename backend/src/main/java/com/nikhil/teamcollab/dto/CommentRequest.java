package com.nikhil.teamcollab.dto;

public class CommentRequest {

    private Long taskId;
    private Long userId;
    private String content;

    public Long getTaskId() {
        return taskId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

package com.nikhil.teamcollab.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String status; // TODO, IN_PROGRESS, DONE

    private String priority; // LOW, MEDIUM, HIGH

    private Long projectId;

    private Long assigneeId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

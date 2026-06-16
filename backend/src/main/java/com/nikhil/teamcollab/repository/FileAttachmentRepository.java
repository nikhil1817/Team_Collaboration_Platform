package com.nikhil.teamcollab.repository;

import com.nikhil.teamcollab.entity.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileAttachmentRepository extends JpaRepository<FileAttachment, Long> {

    List<FileAttachment> findByTaskId(Long taskId);
}

package com.nikhil.teamcollab.controller;

import com.nikhil.teamcollab.entity.FileAttachment;
import com.nikhil.teamcollab.repository.FileAttachmentRepository;
import com.nikhil.teamcollab.service.ActivityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private static final String UPLOAD_DIR = "uploads";

    @Autowired
    private FileAttachmentRepository fileAttachmentRepository;

    @Autowired
    private ActivityService activityService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("taskId") Long taskId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR)
                    .toAbsolutePath()
                    .normalize();

            Files.createDirectories(uploadPath);

            String originalFileName = file.getOriginalFilename();

            if (originalFileName == null || originalFileName.isBlank()) {
                return ResponseEntity.badRequest().body("File name is missing");
            }

            String cleanFileName = originalFileName.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
            String storedFileName = System.currentTimeMillis() + "_" + cleanFileName;

            Path targetPath = uploadPath.resolve(storedFileName);

            Files.copy(
                    file.getInputStream(),
                    targetPath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            FileAttachment attachment = FileAttachment.builder()
                    .taskId(taskId)
                    .fileName(originalFileName)
                    .filePath(targetPath.toString())
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .uploadedAt(LocalDateTime.now())
                    .build();

            FileAttachment savedAttachment = fileAttachmentRepository.save(attachment);

            activityService.addActivity("File uploaded to task: " + taskId);

            return ResponseEntity.ok(savedAttachment);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/task/{taskId}")
    public List<FileAttachment> getFilesByTask(@PathVariable Long taskId) {
        return fileAttachmentRepository.findByTaskId(taskId);
    }
}

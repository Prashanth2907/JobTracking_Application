package com.jobtrack.controller;

import com.jobtrack.dto.response.ApiResponse;
import com.jobtrack.dto.response.ResumeDto;
import com.jobtrack.security.SecurityUtils;
import com.jobtrack.service.ResumeStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
@Tag(name = "Resumes", description = "Resume file upload, download, and management APIs")
public class ResumeController {

    private final ResumeStorageService resumeStorageService;

    public ResumeController(ResumeStorageService resumeStorageService) {
        this.resumeStorageService = resumeStorageService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ROLE_CANDIDATE')")
    @Operation(summary = "Candidate: Upload resume document (PDF, DOC, DOCX)")
    public ResponseEntity<ApiResponse<ResumeDto>> uploadResume(
            @RequestParam("file") MultipartFile file) {
        Long candidateId = SecurityUtils.getCurrentUserId();
        ResumeDto resume = resumeStorageService.uploadResume(candidateId, file);
        return new ResponseEntity<>(ApiResponse.ok("Resume uploaded successfully", resume), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/download")
    @Operation(summary = "Download candidate resume file")
    public ResponseEntity<Resource> downloadResume(@PathVariable Long id) {
        Long currentUserId = null;
        try {
            currentUserId = SecurityUtils.getCurrentUserId();
        } catch (Exception ignored) {
        }

        Resource resource = resumeStorageService.loadResumeAsResource(id, currentUserId);
        ResumeDto metadata = resumeStorageService.getResumeById(id, currentUserId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(metadata.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + metadata.getOriginalFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/my-resumes")
    @PreAuthorize("hasAuthority('ROLE_CANDIDATE')")
    @Operation(summary = "Candidate: List all uploaded resumes")
    public ResponseEntity<ApiResponse<List<ResumeDto>>> getMyResumes() {
        Long candidateId = SecurityUtils.getCurrentUserId();
        List<ResumeDto> resumes = resumeStorageService.getCandidateResumes(candidateId);
        return ResponseEntity.ok(ApiResponse.ok(resumes));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_CANDIDATE')")
    @Operation(summary = "Candidate: Delete an uploaded resume")
    public ResponseEntity<ApiResponse<Void>> deleteResume(@PathVariable Long id) {
        Long candidateId = SecurityUtils.getCurrentUserId();
        resumeStorageService.deleteResume(id, candidateId);
        return ResponseEntity.ok(ApiResponse.ok("Resume deleted successfully", null));
    }
}

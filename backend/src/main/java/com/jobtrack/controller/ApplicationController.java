package com.jobtrack.controller;

import com.jobtrack.dto.request.ApplicationCreateRequest;
import com.jobtrack.dto.request.ApplicationStatusUpdateRequest;
import com.jobtrack.dto.response.ApiResponse;
import com.jobtrack.dto.response.ApplicationDto;
import com.jobtrack.security.SecurityUtils;
import com.jobtrack.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@Tag(name = "Applications", description = "Job application lifecycle, tracking, and review APIs")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CANDIDATE')")
    @Operation(summary = "Candidate: Submit application for a job vacancy")
    public ResponseEntity<ApiResponse<ApplicationDto>> applyForJob(
            @Valid @RequestBody ApplicationCreateRequest request) {
        Long candidateId = SecurityUtils.getCurrentUserId();
        ApplicationDto application = applicationService.applyForJob(candidateId, request);
        return new ResponseEntity<>(ApiResponse.ok("Application submitted successfully", application), HttpStatus.CREATED);
    }

    @GetMapping("/my-applications")
    @PreAuthorize("hasAuthority('ROLE_CANDIDATE')")
    @Operation(summary = "Candidate: Get application history and statuses")
    public ResponseEntity<ApiResponse<List<ApplicationDto>>> getMyApplications() {
        Long candidateId = SecurityUtils.getCurrentUserId();
        List<ApplicationDto> applications = applicationService.getCandidateApplications(candidateId);
        return ResponseEntity.ok(ApiResponse.ok(applications));
    }

    @GetMapping("/job/{jobId}")
    @PreAuthorize("hasAnyAuthority('ROLE_RECRUITER', 'ROLE_ADMIN')")
    @Operation(summary = "Recruiter: Get all applicants for a specific job")
    public ResponseEntity<ApiResponse<List<ApplicationDto>>> getJobApplicants(@PathVariable Long jobId) {
        Long recruiterId = SecurityUtils.getCurrentUserId();
        List<ApplicationDto> applications = applicationService.getJobApplications(jobId, recruiterId);
        return ResponseEntity.ok(ApiResponse.ok(applications));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get application details by ID")
    public ResponseEntity<ApiResponse<ApplicationDto>> getApplicationById(@PathVariable Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        ApplicationDto application = applicationService.getApplicationById(id, userId);
        return ResponseEntity.ok(ApiResponse.ok(application));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('ROLE_RECRUITER', 'ROLE_ADMIN')")
    @Operation(summary = "Recruiter: Advance applicant status (Under Review, Shortlisted, Selected, Rejected)")
    public ResponseEntity<ApiResponse<ApplicationDto>> updateApplicationStatus(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationStatusUpdateRequest request
    ) {
        Long recruiterId = SecurityUtils.getCurrentUserId();
        ApplicationDto updated = applicationService.updateApplicationStatus(id, recruiterId, request);
        return ResponseEntity.ok(ApiResponse.ok("Application status updated to " + request.getStatus(), updated));
    }
}

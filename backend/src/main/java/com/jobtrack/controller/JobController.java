package com.jobtrack.controller;

import com.jobtrack.dto.request.JobCreateRequest;
import com.jobtrack.dto.request.JobUpdateRequest;
import com.jobtrack.dto.response.ApiResponse;
import com.jobtrack.dto.response.JobDto;
import com.jobtrack.entity.EmploymentType;
import com.jobtrack.entity.JobStatus;
import com.jobtrack.security.SecurityUtils;
import com.jobtrack.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@Tag(name = "Jobs", description = "Job vacancy management, search, and filtering APIs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    @Operation(summary = "Search, filter, and paginate published job vacancies")
    public ResponseEntity<ApiResponse<Page<JobDto>>> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) EmploymentType employmentType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long currentUserId = null;
        try {
            currentUserId = SecurityUtils.getCurrentUserId();
        } catch (Exception ignored) {
        }

        Page<JobDto> jobs = jobService.searchJobs(keyword, location, employmentType, page, size, currentUserId);
        return ResponseEntity.ok(ApiResponse.ok(jobs));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get full job details by ID")
    public ResponseEntity<ApiResponse<JobDto>> getJobById(@PathVariable Long id) {
        Long currentUserId = null;
        try {
            currentUserId = SecurityUtils.getCurrentUserId();
        } catch (Exception ignored) {
        }

        JobDto job = jobService.getJobById(id, currentUserId);
        return ResponseEntity.ok(ApiResponse.ok(job));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_RECRUITER')")
    @Operation(summary = "Recruiter: Publish a new job vacancy")
    public ResponseEntity<ApiResponse<JobDto>> createJob(@Valid @RequestBody JobCreateRequest request) {
        Long recruiterId = SecurityUtils.getCurrentUserId();
        JobDto createdJob = jobService.createJob(recruiterId, request);
        return new ResponseEntity<>(ApiResponse.ok("Job vacancy published successfully", createdJob), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_RECRUITER')")
    @Operation(summary = "Recruiter: Update existing job vacancy")
    public ResponseEntity<ApiResponse<JobDto>> updateJob(
            @PathVariable Long id,
            @RequestBody JobUpdateRequest request
    ) {
        Long recruiterId = SecurityUtils.getCurrentUserId();
        JobDto updated = jobService.updateJob(id, recruiterId, request);
        return ResponseEntity.ok(ApiResponse.ok("Job updated successfully", updated));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('ROLE_RECRUITER', 'ROLE_ADMIN')")
    @Operation(summary = "Recruiter/Admin: Update job status (PUBLISHED, CLOSED, DRAFT)")
    public ResponseEntity<ApiResponse<JobDto>> updateJobStatus(
            @PathVariable Long id,
            @RequestParam JobStatus status
    ) {
        Long recruiterId = SecurityUtils.getCurrentUserId();
        JobDto updated = jobService.updateJobStatus(id, recruiterId, status);
        return ResponseEntity.ok(ApiResponse.ok("Job status updated to " + status, updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_RECRUITER', 'ROLE_ADMIN')")
    @Operation(summary = "Recruiter/Admin: Delete a job vacancy")
    public ResponseEntity<ApiResponse<Void>> deleteJob(@PathVariable Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        jobService.deleteJob(id, userId);
        return ResponseEntity.ok(ApiResponse.ok("Job vacancy deleted successfully", null));
    }

    @GetMapping("/recruiter/my-jobs")
    @PreAuthorize("hasAuthority('ROLE_RECRUITER')")
    @Operation(summary = "Recruiter: Get all jobs posted by current recruiter")
    public ResponseEntity<ApiResponse<List<JobDto>>> getMyPostedJobs() {
        Long recruiterId = SecurityUtils.getCurrentUserId();
        List<JobDto> jobs = jobService.getRecruiterJobs(recruiterId);
        return ResponseEntity.ok(ApiResponse.ok(jobs));
    }
}

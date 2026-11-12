package com.jobtrack.controller;

import com.jobtrack.dto.request.InterviewScheduleRequest;
import com.jobtrack.dto.response.ApiResponse;
import com.jobtrack.dto.response.InterviewDto;
import com.jobtrack.entity.Role;
import com.jobtrack.security.SecurityUtils;
import com.jobtrack.security.UserPrincipal;
import com.jobtrack.service.InterviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
@Tag(name = "Interviews", description = "Interview scheduling, calendar, and coordination APIs")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_RECRUITER')")
    @Operation(summary = "Recruiter: Schedule an interview for a shortlisted candidate")
    public ResponseEntity<ApiResponse<InterviewDto>> scheduleInterview(
            @Valid @RequestBody InterviewScheduleRequest request) {
        Long recruiterId = SecurityUtils.getCurrentUserId();
        InterviewDto interview = interviewService.scheduleInterview(recruiterId, request);
        return new ResponseEntity<>(ApiResponse.ok("Interview scheduled successfully", interview), HttpStatus.CREATED);
    }

    @GetMapping("/my-interviews")
    @Operation(summary = "Get user interview schedule (Candidate or Recruiter)")
    public ResponseEntity<ApiResponse<List<InterviewDto>>> getMyInterviews() {
        UserPrincipal principal = SecurityUtils.getCurrentUserPrincipal();
        boolean isRecruiter = principal.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(Role.ROLE_RECRUITER.name()));

        List<InterviewDto> interviews;
        if (isRecruiter) {
            interviews = interviewService.getRecruiterInterviews(principal.getId());
        } else {
            interviews = interviewService.getCandidateInterviews(principal.getId());
        }

        return ResponseEntity.ok(ApiResponse.ok(interviews));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_RECRUITER')")
    @Operation(summary = "Recruiter: Reschedule or edit interview details")
    public ResponseEntity<ApiResponse<InterviewDto>> updateInterview(
            @PathVariable Long id,
            @Valid @RequestBody InterviewScheduleRequest request) {
        Long recruiterId = SecurityUtils.getCurrentUserId();
        InterviewDto updated = interviewService.updateInterview(id, recruiterId, request);
        return ResponseEntity.ok(ApiResponse.ok("Interview updated successfully", updated));
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('ROLE_RECRUITER')")
    @Operation(summary = "Recruiter: Cancel an interview")
    public ResponseEntity<ApiResponse<Void>> cancelInterview(@PathVariable Long id) {
        Long recruiterId = SecurityUtils.getCurrentUserId();
        interviewService.cancelInterview(id, recruiterId);
        return ResponseEntity.ok(ApiResponse.ok("Interview cancelled successfully", null));
    }
}

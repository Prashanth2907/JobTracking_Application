package com.jobtrack.controller;

import com.jobtrack.dto.response.ApiResponse;
import com.jobtrack.dto.response.CandidateDashboardDto;
import com.jobtrack.dto.response.RecruiterDashboardDto;
import com.jobtrack.security.SecurityUtils;
import com.jobtrack.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Dashboards", description = "Role-specific aggregate metrics for Candidate and Recruiter portals")
public class DashboardController {

    private final AdminService adminService;

    public DashboardController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/candidate/dashboard")
    @PreAuthorize("hasAuthority('ROLE_CANDIDATE')")
    @Operation(summary = "Get candidate dashboard summary (applications, upcoming interviews, profile completion)")
    public ResponseEntity<ApiResponse<CandidateDashboardDto>> getCandidateDashboard() {
        Long candidateId = SecurityUtils.getCurrentUserId();
        CandidateDashboardDto dashboard = adminService.getCandidateDashboard(candidateId);
        return ResponseEntity.ok(ApiResponse.ok(dashboard));
    }

    @GetMapping("/recruiter/dashboard")
    @PreAuthorize("hasAuthority('ROLE_RECRUITER')")
    @Operation(summary = "Get recruiter dashboard summary (active jobs, applicants, pipeline stats, interviews)")
    public ResponseEntity<ApiResponse<RecruiterDashboardDto>> getRecruiterDashboard() {
        Long recruiterId = SecurityUtils.getCurrentUserId();
        RecruiterDashboardDto dashboard = adminService.getRecruiterDashboard(recruiterId);
        return ResponseEntity.ok(ApiResponse.ok(dashboard));
    }
}

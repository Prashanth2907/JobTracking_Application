package com.jobtrack.controller;

import com.jobtrack.dto.response.*;
import com.jobtrack.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Tag(name = "Admin", description = "Administrator platform oversight, user moderation, and metrics")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/dashboard")
    @Operation(summary = "Get platform-wide admin metrics and summary")
    public ResponseEntity<ApiResponse<AdminDashboardDto>> getDashboardStats() {
        AdminDashboardDto stats = adminService.getAdminDashboard();
        return ResponseEntity.ok(ApiResponse.ok(stats));
    }

    @GetMapping("/users")
    @Operation(summary = "Get all registered users with pagination")
    public ResponseEntity<ApiResponse<Page<UserDto>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<UserDto> users = adminService.getAllUsers(page, size);
        return ResponseEntity.ok(ApiResponse.ok(users));
    }

    @PatchMapping("/users/{id}/toggle-status")
    @Operation(summary = "Activate or deactivate a user account")
    public ResponseEntity<ApiResponse<UserDto>> toggleUserStatus(@PathVariable Long id) {
        UserDto updated = adminService.toggleUserStatus(id);
        return ResponseEntity.ok(ApiResponse.ok("User status updated to " + updated.getStatus(), updated));
    }

    @GetMapping("/jobs")
    @Operation(summary = "Get all platform job vacancies with pagination")
    public ResponseEntity<ApiResponse<Page<JobDto>>> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<JobDto> jobs = adminService.getAllJobs(page, size);
        return ResponseEntity.ok(ApiResponse.ok(jobs));
    }

    @GetMapping("/applications")
    @Operation(summary = "Get all job applications on the platform with pagination")
    public ResponseEntity<ApiResponse<Page<ApplicationDto>>> getAllApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ApplicationDto> applications = adminService.getAllApplications(page, size);
        return ResponseEntity.ok(ApiResponse.ok(applications));
    }
}

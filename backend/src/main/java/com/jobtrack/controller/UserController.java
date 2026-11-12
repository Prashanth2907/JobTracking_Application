package com.jobtrack.controller;

import com.jobtrack.dto.request.CandidateProfileUpdateRequest;
import com.jobtrack.dto.request.RecruiterProfileUpdateRequest;
import com.jobtrack.dto.response.ApiResponse;
import com.jobtrack.dto.response.CandidateProfileDto;
import com.jobtrack.dto.response.RecruiterProfileDto;
import com.jobtrack.dto.response.UserDto;
import com.jobtrack.security.SecurityUtils;
import com.jobtrack.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Profile", description = "Candidate and recruiter profile management APIs")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/candidate-profile")
    @PreAuthorize("hasAuthority('ROLE_CANDIDATE')")
    @Operation(summary = "Get candidate profile of current authenticated user")
    public ResponseEntity<ApiResponse<CandidateProfileDto>> getMyCandidateProfile() {
        Long userId = SecurityUtils.getCurrentUserId();
        CandidateProfileDto profile = userService.getCandidateProfile(userId);
        return ResponseEntity.ok(ApiResponse.ok(profile));
    }

    @PutMapping("/candidate-profile")
    @PreAuthorize("hasAuthority('ROLE_CANDIDATE')")
    @Operation(summary = "Update candidate profile (skills, education, experience, bio)")
    public ResponseEntity<ApiResponse<CandidateProfileDto>> updateCandidateProfile(
            @RequestBody CandidateProfileUpdateRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        CandidateProfileDto profile = userService.updateCandidateProfile(userId, request);
        return ResponseEntity.ok(ApiResponse.ok("Candidate profile updated successfully", profile));
    }

    @GetMapping("/candidate-profile/{candidateId}")
    @PreAuthorize("hasAnyAuthority('ROLE_RECRUITER', 'ROLE_ADMIN')")
    @Operation(summary = "Recruiter/Admin: View candidate profile details")
    public ResponseEntity<ApiResponse<CandidateProfileDto>> getCandidateProfileById(
            @PathVariable Long candidateId) {
        CandidateProfileDto profile = userService.getCandidateProfile(candidateId);
        return ResponseEntity.ok(ApiResponse.ok(profile));
    }

    @GetMapping("/recruiter-profile")
    @PreAuthorize("hasAuthority('ROLE_RECRUITER')")
    @Operation(summary = "Get recruiter & company profile of current user")
    public ResponseEntity<ApiResponse<RecruiterProfileDto>> getMyRecruiterProfile() {
        Long userId = SecurityUtils.getCurrentUserId();
        RecruiterProfileDto profile = userService.getRecruiterProfile(userId);
        return ResponseEntity.ok(ApiResponse.ok(profile));
    }

    @PutMapping("/recruiter-profile")
    @PreAuthorize("hasAuthority('ROLE_RECRUITER')")
    @Operation(summary = "Update recruiter and company details")
    public ResponseEntity<ApiResponse<RecruiterProfileDto>> updateRecruiterProfile(
            @RequestBody RecruiterProfileUpdateRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        RecruiterProfileDto profile = userService.updateRecruiterProfile(userId, request);
        return ResponseEntity.ok(ApiResponse.ok("Recruiter profile updated successfully", profile));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user information by user ID")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.ok(user));
    }
}

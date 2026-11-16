package com.jobtrack.controller;

import com.jobtrack.dto.request.CandidateRegisterRequest;
import com.jobtrack.dto.request.LoginRequest;
import com.jobtrack.dto.request.RecruiterRegisterRequest;
import com.jobtrack.dto.request.RegisterRequest;
import com.jobtrack.dto.response.ApiResponse;
import com.jobtrack.dto.response.AuthResponse;
import com.jobtrack.dto.response.UserDto;
import com.jobtrack.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "User registration, login, and token session verification")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new candidate or recruiter account")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return new ResponseEntity<>(ApiResponse.ok("User registered successfully", response), HttpStatus.CREATED);
    }

    @PostMapping("/register/candidate")
    @Operation(summary = "Register a new candidate job seeker account")
    public ResponseEntity<ApiResponse<AuthResponse>> registerCandidate(@Valid @RequestBody CandidateRegisterRequest request) {
        AuthResponse response = authService.registerCandidate(request);
        return new ResponseEntity<>(ApiResponse.ok("Candidate registered successfully", response), HttpStatus.CREATED);
    }

    @PostMapping("/register/recruiter")
    @Operation(summary = "Register a new recruiter hiring manager account")
    public ResponseEntity<ApiResponse<AuthResponse>> registerRecruiter(@Valid @RequestBody RecruiterRegisterRequest request) {
        AuthResponse response = authService.registerRecruiter(request);
        return new ResponseEntity<>(ApiResponse.ok("Recruiter registered successfully", response), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user and generate JWT access token")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.ok("Login successful", response));
    }

    @GetMapping("/me")
    @Operation(summary = "Get current authenticated user profile")
    public ResponseEntity<ApiResponse<UserDto>> getCurrentUser() {
        UserDto user = authService.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.ok(user));
    }
}


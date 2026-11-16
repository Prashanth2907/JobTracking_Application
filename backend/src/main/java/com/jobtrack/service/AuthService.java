package com.jobtrack.service;

import com.jobtrack.dto.request.CandidateRegisterRequest;
import com.jobtrack.dto.request.LoginRequest;
import com.jobtrack.dto.request.RecruiterRegisterRequest;
import com.jobtrack.dto.request.RegisterRequest;
import com.jobtrack.dto.response.AuthResponse;
import com.jobtrack.dto.response.UserDto;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse registerCandidate(CandidateRegisterRequest request);
    AuthResponse registerRecruiter(RecruiterRegisterRequest request);
    AuthResponse login(LoginRequest request);
    UserDto getCurrentUser();
}


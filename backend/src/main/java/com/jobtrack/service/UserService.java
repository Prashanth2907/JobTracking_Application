package com.jobtrack.service;

import com.jobtrack.dto.request.CandidateProfileUpdateRequest;
import com.jobtrack.dto.request.RecruiterProfileUpdateRequest;
import com.jobtrack.dto.response.CandidateProfileDto;
import com.jobtrack.dto.response.RecruiterProfileDto;
import com.jobtrack.dto.response.UserDto;

public interface UserService {
    CandidateProfileDto getCandidateProfile(Long userId);
    CandidateProfileDto updateCandidateProfile(Long userId, CandidateProfileUpdateRequest request);
    RecruiterProfileDto getRecruiterProfile(Long userId);
    RecruiterProfileDto updateRecruiterProfile(Long userId, RecruiterProfileUpdateRequest request);
    UserDto getUserById(Long userId);
}

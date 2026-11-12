package com.jobtrack.service;

import com.jobtrack.dto.response.*;
import org.springframework.data.domain.Page;

public interface AdminService {
    AdminDashboardDto getAdminDashboard();
    Page<UserDto> getAllUsers(int page, int size);
    UserDto toggleUserStatus(Long userId);
    Page<JobDto> getAllJobs(int page, int size);
    Page<ApplicationDto> getAllApplications(int page, int size);
    CandidateDashboardDto getCandidateDashboard(Long candidateId);
    RecruiterDashboardDto getRecruiterDashboard(Long recruiterId);
}

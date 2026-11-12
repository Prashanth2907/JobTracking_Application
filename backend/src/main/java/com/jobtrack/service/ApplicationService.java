package com.jobtrack.service;

import com.jobtrack.dto.request.ApplicationCreateRequest;
import com.jobtrack.dto.request.ApplicationStatusUpdateRequest;
import com.jobtrack.dto.response.ApplicationDto;

import java.util.List;

public interface ApplicationService {
    ApplicationDto applyForJob(Long candidateId, ApplicationCreateRequest request);
    List<ApplicationDto> getCandidateApplications(Long candidateId);
    List<ApplicationDto> getJobApplications(Long jobId, Long recruiterId);
    ApplicationDto getApplicationById(Long applicationId, Long userId);
    ApplicationDto updateApplicationStatus(Long applicationId, Long recruiterId, ApplicationStatusUpdateRequest request);
}

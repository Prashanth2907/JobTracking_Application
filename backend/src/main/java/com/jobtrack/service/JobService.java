package com.jobtrack.service;

import com.jobtrack.dto.request.JobCreateRequest;
import com.jobtrack.dto.request.JobUpdateRequest;
import com.jobtrack.dto.response.JobDto;
import com.jobtrack.entity.EmploymentType;
import com.jobtrack.entity.JobStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JobService {
    JobDto createJob(Long recruiterId, JobCreateRequest request);
    JobDto updateJob(Long jobId, Long recruiterId, JobUpdateRequest request);
    JobDto updateJobStatus(Long jobId, Long recruiterId, JobStatus status);
    void deleteJob(Long jobId, Long userId);
    JobDto getJobById(Long jobId, Long currentUserId);
    Page<JobDto> searchJobs(String keyword, String location, EmploymentType employmentType, int page, int size, Long currentUserId);
    List<JobDto> getRecruiterJobs(Long recruiterId);
}

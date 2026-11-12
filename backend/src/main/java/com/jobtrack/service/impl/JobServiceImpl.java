package com.jobtrack.service.impl;

import com.jobtrack.dto.request.JobCreateRequest;
import com.jobtrack.dto.request.JobUpdateRequest;
import com.jobtrack.dto.response.JobDto;
import com.jobtrack.entity.*;
import com.jobtrack.exception.ResourceNotFoundException;
import com.jobtrack.exception.UnauthorizedException;
import com.jobtrack.mapper.EntityMapper;
import com.jobtrack.repository.*;
import com.jobtrack.service.JobService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final ApplicationRepository applicationRepository;

    public JobServiceImpl(JobRepository jobRepository, UserRepository userRepository, RecruiterProfileRepository recruiterProfileRepository, ApplicationRepository applicationRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    @Transactional
    public JobDto createJob(Long recruiterId, JobCreateRequest request) {
        User recruiter = userRepository.findById(recruiterId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", recruiterId));

        RecruiterProfile recruiterProfile = recruiterProfileRepository.findByUser(recruiter).orElse(null);
        Company company = recruiterProfile != null ? recruiterProfile.getCompany() : null;

        String companyName = request.getCompanyName();
        if (companyName == null || companyName.trim().isEmpty()) {
            if (company != null && company.getName() != null) {
                companyName = company.getName();
            } else {
                companyName = "Company Confidential";
            }
        }

        Job job = Job.builder()
                .recruiter(recruiter)
                .company(company)
                .companyName(companyName.trim())
                .title(request.getTitle().trim())
                .description(request.getDescription().trim())
                .requirements(request.getRequirements())
                .skillsRequired(request.getSkillsRequired().trim())
                .location(request.getLocation().trim())
                .employmentType(request.getEmploymentType() != null ? request.getEmploymentType() : EmploymentType.FULL_TIME)
                .experienceRequired(request.getExperienceRequired())
                .salaryMin(request.getSalaryMin())
                .salaryMax(request.getSalaryMax())
                .vacanciesCount(request.getVacanciesCount() != null ? request.getVacanciesCount() : 1)
                .deadline(request.getDeadline())
                .status(request.getStatus() != null ? request.getStatus() : JobStatus.PUBLISHED)
                .build();

        Job savedJob = jobRepository.save(job);
        return EntityMapper.toJobDto(savedJob, 0L, false);
    }

    @Override
    @Transactional
    public JobDto updateJob(Long jobId, Long recruiterId, JobUpdateRequest request) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));

        if (!job.getRecruiter().getId().equals(recruiterId)) {
            User user = userRepository.findById(recruiterId).orElse(null);
            if (user == null || user.getRole() != Role.ROLE_ADMIN) {
                throw new UnauthorizedException("You are not authorized to edit this job posting");
            }
        }

        if (request.getTitle() != null && !request.getTitle().trim().isEmpty()) {
            job.setTitle(request.getTitle().trim());
        }
        if (request.getCompanyName() != null && !request.getCompanyName().trim().isEmpty()) {
            job.setCompanyName(request.getCompanyName().trim());
        }
        if (request.getDescription() != null && !request.getDescription().trim().isEmpty()) {
            job.setDescription(request.getDescription().trim());
        }
        if (request.getRequirements() != null) {
            job.setRequirements(request.getRequirements());
        }
        if (request.getSkillsRequired() != null && !request.getSkillsRequired().trim().isEmpty()) {
            job.setSkillsRequired(request.getSkillsRequired().trim());
        }
        if (request.getLocation() != null && !request.getLocation().trim().isEmpty()) {
            job.setLocation(request.getLocation().trim());
        }
        if (request.getEmploymentType() != null) {
            job.setEmploymentType(request.getEmploymentType());
        }
        if (request.getExperienceRequired() != null) {
            job.setExperienceRequired(request.getExperienceRequired());
        }
        if (request.getSalaryMin() != null) {
            job.setSalaryMin(request.getSalaryMin());
        }
        if (request.getSalaryMax() != null) {
            job.setSalaryMax(request.getSalaryMax());
        }
        if (request.getVacanciesCount() != null) {
            job.setVacanciesCount(request.getVacanciesCount());
        }
        if (request.getDeadline() != null) {
            job.setDeadline(request.getDeadline());
        }
        if (request.getStatus() != null) {
            job.setStatus(request.getStatus());
        }

        Job updatedJob = jobRepository.save(job);
        long applicantCount = applicationRepository.findByJobOrderByAppliedAtDesc(updatedJob).size();
        return EntityMapper.toJobDto(updatedJob, applicantCount, false);
    }

    @Override
    @Transactional
    public JobDto updateJobStatus(Long jobId, Long recruiterId, JobStatus status) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));

        if (!job.getRecruiter().getId().equals(recruiterId)) {
            User user = userRepository.findById(recruiterId).orElse(null);
            if (user == null || user.getRole() != Role.ROLE_ADMIN) {
                throw new UnauthorizedException("You are not authorized to update status for this job posting");
            }
        }

        job.setStatus(status);
        Job updated = jobRepository.save(job);
        long applicantCount = applicationRepository.findByJobOrderByAppliedAtDesc(updated).size();
        return EntityMapper.toJobDto(updated, applicantCount, false);
    }

    @Override
    @Transactional
    public void deleteJob(Long jobId, Long userId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (!job.getRecruiter().getId().equals(userId) && user.getRole() != Role.ROLE_ADMIN) {
            throw new UnauthorizedException("You are not authorized to delete this job posting");
        }

        jobRepository.delete(job);
    }

    @Override
    @Transactional(readOnly = true)
    public JobDto getJobById(Long jobId, Long currentUserId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));

        long applicantCount = applicationRepository.findByJobOrderByAppliedAtDesc(job).size();
        boolean hasApplied = false;
        if (currentUserId != null) {
            hasApplied = applicationRepository.existsByJobIdAndCandidateId(jobId, currentUserId);
        }

        return EntityMapper.toJobDto(job, applicantCount, hasApplied);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobDto> searchJobs(String keyword, String location, EmploymentType employmentType, int page, int size, Long currentUserId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("postedDate").descending());
        Page<Job> jobs = jobRepository.searchJobs(JobStatus.PUBLISHED, keyword, location, employmentType, pageable);

        return jobs.map(job -> {
            long count = applicationRepository.findByJobOrderByAppliedAtDesc(job).size();
            boolean hasApplied = false;
            if (currentUserId != null) {
                hasApplied = applicationRepository.existsByJobIdAndCandidateId(job.getId(), currentUserId);
            }
            return EntityMapper.toJobDto(job, count, hasApplied);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobDto> getRecruiterJobs(Long recruiterId) {
        User recruiter = userRepository.findById(recruiterId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", recruiterId));

        List<Job> jobs = jobRepository.findByRecruiterOrderByPostedDateDesc(recruiter);
        return jobs.stream().map(job -> {
            long count = applicationRepository.findByJobOrderByAppliedAtDesc(job).size();
            return EntityMapper.toJobDto(job, count, false);
        }).collect(Collectors.toList());
    }
}

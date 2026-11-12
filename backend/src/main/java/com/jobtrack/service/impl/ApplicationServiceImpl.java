package com.jobtrack.service.impl;

import com.jobtrack.dto.request.ApplicationCreateRequest;
import com.jobtrack.dto.request.ApplicationStatusUpdateRequest;
import com.jobtrack.dto.response.ApplicationDto;
import com.jobtrack.entity.*;
import com.jobtrack.exception.BadRequestException;
import com.jobtrack.exception.ResourceNotFoundException;
import com.jobtrack.exception.UnauthorizedException;
import com.jobtrack.mapper.EntityMapper;
import com.jobtrack.repository.*;
import com.jobtrack.service.ApplicationService;
import com.jobtrack.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final NotificationService notificationService;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, JobRepository jobRepository, UserRepository userRepository, ResumeRepository resumeRepository, CandidateProfileRepository candidateProfileRepository, NotificationService notificationService) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.resumeRepository = resumeRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public ApplicationDto applyForJob(Long candidateId, ApplicationCreateRequest request) {
        User candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", candidateId));

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", request.getJobId()));

        if (job.getStatus() != JobStatus.PUBLISHED) {
            throw new BadRequestException("This job vacancy is no longer accepting applications");
        }

        if (applicationRepository.existsByJobIdAndCandidateId(job.getId(), candidate.getId())) {
            throw new BadRequestException("You have already submitted an application for this position");
        }

        Resume resume = null;
        if (request.getResumeId() != null) {
            resume = resumeRepository.findByIdAndCandidate(request.getResumeId(), candidate)
                    .orElseThrow(() -> new BadRequestException("Selected resume was not found"));
        } else {
            CandidateProfile profile = candidateProfileRepository.findByUser(candidate).orElse(null);
            if (profile != null && profile.getActiveResume() != null) {
                resume = profile.getActiveResume();
            } else {
                resume = resumeRepository.findTopByCandidateOrderByUploadedAtDesc(candidate).orElse(null);
            }
        }

        Application application = Application.builder()
                .job(job)
                .candidate(candidate)
                .resume(resume)
                .coverLetter(request.getCoverLetter())
                .status(ApplicationStatus.APPLIED)
                .build();

        Application saved = applicationRepository.save(application);

        notificationService.sendNotification(
                job.getRecruiter(),
                "New Application Received",
                candidate.getFullName() + " applied for '" + job.getTitle() + "'",
                "NEW_APPLICATION",
                "/recruiter/jobs/" + job.getId() + "/applicants"
        );

        notificationService.sendNotification(
                candidate,
                "Application Submitted Successfully",
                "Your application for '" + job.getTitle() + "' at " + job.getCompanyName() + " has been received.",
                "APPLICATION_SUBMITTED",
                "/candidate/applications"
        );

        return EntityMapper.toApplicationDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationDto> getCandidateApplications(Long candidateId) {
        User candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", candidateId));

        return applicationRepository.findByCandidateOrderByAppliedAtDesc(candidate)
                .stream()
                .map(EntityMapper::toApplicationDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationDto> getJobApplications(Long jobId, Long recruiterId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));

        User user = userRepository.findById(recruiterId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", recruiterId));

        if (!job.getRecruiter().getId().equals(recruiterId) && user.getRole() != Role.ROLE_ADMIN) {
            throw new UnauthorizedException("You are not authorized to view applications for this job posting");
        }

        return applicationRepository.findByJobOrderByAppliedAtDesc(job)
                .stream()
                .map(EntityMapper::toApplicationDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationDto getApplicationById(Long applicationId, Long userId) {
        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application", "id", applicationId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        boolean isApplicant = app.getCandidate().getId().equals(userId);
        boolean isRecruiter = app.getJob().getRecruiter().getId().equals(userId);
        boolean isAdmin = user.getRole() == Role.ROLE_ADMIN;

        if (!isApplicant && !isRecruiter && !isAdmin) {
            throw new UnauthorizedException("You are not authorized to view this application");
        }

        return EntityMapper.toApplicationDto(app);
    }

    @Override
    @Transactional
    public ApplicationDto updateApplicationStatus(Long applicationId, Long recruiterId, ApplicationStatusUpdateRequest request) {
        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application", "id", applicationId));

        User recruiter = userRepository.findById(recruiterId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", recruiterId));

        if (!app.getJob().getRecruiter().getId().equals(recruiterId) && recruiter.getRole() != Role.ROLE_ADMIN) {
            throw new UnauthorizedException("You are not authorized to update this candidate's application status");
        }

        app.setStatus(request.getStatus());
        Application saved = applicationRepository.save(app);

        String statusLabel = request.getStatus().name().replace('_', ' ');
        String noteMsg = (request.getNote() != null && !request.getNote().trim().isEmpty())
                ? " Note from recruiter: " + request.getNote().trim() : "";

        notificationService.sendNotification(
                app.getCandidate(),
                "Application Status Update: " + statusLabel,
                "Your application for '" + app.getJob().getTitle() + "' at " + app.getJob().getCompanyName()
                        + " has been updated to: " + statusLabel + "." + noteMsg,
                "APPLICATION_STATUS",
                "/candidate/applications"
        );

        return EntityMapper.toApplicationDto(saved);
    }
}

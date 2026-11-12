package com.jobtrack.service;

import com.jobtrack.dto.request.ApplicationCreateRequest;
import com.jobtrack.dto.request.ApplicationStatusUpdateRequest;
import com.jobtrack.dto.response.ApplicationDto;
import com.jobtrack.entity.*;
import com.jobtrack.exception.BadRequestException;
import com.jobtrack.repository.*;
import com.jobtrack.service.impl.ApplicationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ResumeRepository resumeRepository;

    @Mock
    private CandidateProfileRepository candidateProfileRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    private User candidate;
    private User recruiter;
    private Job job;
    private Application application;

    @BeforeEach
    void setUp() {
        candidate = User.builder().id(4L).email("john@example.com").fullName("John Doe").role(Role.ROLE_CANDIDATE).status(UserStatus.ACTIVE).build();
        recruiter = User.builder().id(2L).email("sarah@example.com").fullName("Sarah Jenkins").role(Role.ROLE_RECRUITER).status(UserStatus.ACTIVE).build();

        job = Job.builder()
                .id(1L)
                .recruiter(recruiter)
                .title("Java Developer")
                .companyName("TechCorp")
                .status(JobStatus.PUBLISHED)
                .skillsRequired("Java")
                .location("Remote")
                .build();

        application = Application.builder()
                .id(100L)
                .job(job)
                .candidate(candidate)
                .status(ApplicationStatus.APPLIED)
                .build();
    }

    @Test
    @DisplayName("Should successfully submit job application")
    void applyForJob_Success() {
        ApplicationCreateRequest request = ApplicationCreateRequest.builder()
                .jobId(1L)
                .coverLetter("Great opportunity")
                .build();

        when(userRepository.findById(4L)).thenReturn(Optional.of(candidate));
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        when(applicationRepository.existsByJobIdAndCandidateId(1L, 4L)).thenReturn(false);
        when(candidateProfileRepository.findByUser(candidate)).thenReturn(Optional.empty());
        when(resumeRepository.findTopByCandidateOrderByUploadedAtDesc(candidate)).thenReturn(Optional.empty());
        when(applicationRepository.save(any(Application.class))).thenReturn(application);

        ApplicationDto result = applicationService.applyForJob(4L, request);

        assertNotNull(result);
        assertEquals(ApplicationStatus.APPLIED, result.getStatus());
        verify(notificationService, times(2)).sendNotification(any(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("Should prevent duplicate applications to same job")
    void applyForJob_DuplicatePrevention() {
        ApplicationCreateRequest request = ApplicationCreateRequest.builder().jobId(1L).build();

        when(userRepository.findById(4L)).thenReturn(Optional.of(candidate));
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        when(applicationRepository.existsByJobIdAndCandidateId(1L, 4L)).thenReturn(true);

        assertThrows(BadRequestException.class, () -> applicationService.applyForJob(4L, request));
        verify(applicationRepository, never()).save(any(Application.class));
    }

    @Test
    @DisplayName("Should update application status and notify candidate")
    void updateApplicationStatus_Success() {
        ApplicationStatusUpdateRequest request = ApplicationStatusUpdateRequest.builder()
                .status(ApplicationStatus.SHORTLISTED)
                .note("Selected for technical interview")
                .build();

        when(applicationRepository.findById(100L)).thenReturn(Optional.of(application));
        when(userRepository.findById(2L)).thenReturn(Optional.of(recruiter));
        when(applicationRepository.save(any(Application.class))).thenReturn(application);

        ApplicationDto updated = applicationService.updateApplicationStatus(100L, 2L, request);

        assertNotNull(updated);
        assertEquals(ApplicationStatus.SHORTLISTED, application.getStatus());
        verify(notificationService, times(1)).sendNotification(any(), anyString(), anyString(), anyString(), anyString());
    }
}

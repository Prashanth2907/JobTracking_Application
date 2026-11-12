package com.jobtrack.service;

import com.jobtrack.dto.request.JobCreateRequest;
import com.jobtrack.dto.response.JobDto;
import com.jobtrack.entity.*;
import com.jobtrack.repository.ApplicationRepository;
import com.jobtrack.repository.JobRepository;
import com.jobtrack.repository.RecruiterProfileRepository;
import com.jobtrack.repository.UserRepository;
import com.jobtrack.service.impl.JobServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecruiterProfileRepository recruiterProfileRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private JobServiceImpl jobService;

    private User recruiter;
    private Job sampleJob;

    @BeforeEach
    void setUp() {
        recruiter = User.builder()
                .id(2L)
                .email("recruiter@example.com")
                .fullName("Sarah Recruiter")
                .role(Role.ROLE_RECRUITER)
                .status(UserStatus.ACTIVE)
                .build();

        sampleJob = Job.builder()
                .id(10L)
                .recruiter(recruiter)
                .title("Full Stack Java Engineer")
                .companyName("TechCorp")
                .description("Build Spring Boot APIs")
                .skillsRequired("Java, Spring Boot, React")
                .location("Remote")
                .employmentType(EmploymentType.FULL_TIME)
                .status(JobStatus.PUBLISHED)
                .salaryMin(BigDecimal.valueOf(100000))
                .salaryMax(BigDecimal.valueOf(130000))
                .build();
    }

    @Test
    @DisplayName("Should create job vacancy successfully")
    void createJob_Success() {
        JobCreateRequest request = JobCreateRequest.builder()
                .title("Full Stack Java Engineer")
                .companyName("TechCorp")
                .description("Build Spring Boot APIs")
                .skillsRequired("Java, Spring Boot, React")
                .location("Remote")
                .employmentType(EmploymentType.FULL_TIME)
                .salaryMin(BigDecimal.valueOf(100000))
                .salaryMax(BigDecimal.valueOf(130000))
                .build();

        when(userRepository.findById(2L)).thenReturn(Optional.of(recruiter));
        when(recruiterProfileRepository.findByUser(recruiter)).thenReturn(Optional.empty());
        when(jobRepository.save(any(Job.class))).thenReturn(sampleJob);

        JobDto created = jobService.createJob(2L, request);

        assertNotNull(created);
        assertEquals("Full Stack Java Engineer", created.getTitle());
        assertEquals("TechCorp", created.getCompanyName());
        verify(jobRepository, times(1)).save(any(Job.class));
    }

    @Test
    @DisplayName("Should retrieve job by ID")
    void getJobById_Success() {
        when(jobRepository.findById(10L)).thenReturn(Optional.of(sampleJob));
        when(applicationRepository.findByJobOrderByAppliedAtDesc(sampleJob)).thenReturn(Collections.emptyList());

        JobDto job = jobService.getJobById(10L, null);

        assertNotNull(job);
        assertEquals(10L, job.getId());
        assertEquals("Full Stack Java Engineer", job.getTitle());
    }
}

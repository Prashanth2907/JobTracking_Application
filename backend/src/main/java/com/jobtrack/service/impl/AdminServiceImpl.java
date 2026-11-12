package com.jobtrack.service.impl;

import com.jobtrack.dto.response.*;
import com.jobtrack.entity.*;
import com.jobtrack.exception.ResourceNotFoundException;
import com.jobtrack.mapper.EntityMapper;
import com.jobtrack.repository.*;
import com.jobtrack.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;
    private final CandidateProfileRepository candidateProfileRepository;

    public AdminServiceImpl(UserRepository userRepository, CompanyRepository companyRepository, JobRepository jobRepository, ApplicationRepository applicationRepository, InterviewRepository interviewRepository, CandidateProfileRepository candidateProfileRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.interviewRepository = interviewRepository;
        this.candidateProfileRepository = candidateProfileRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public AdminDashboardDto getAdminDashboard() {
        long totalUsers = userRepository.count();
        long totalCandidates = userRepository.countByRole(Role.ROLE_CANDIDATE);
        long totalRecruiters = userRepository.countByRole(Role.ROLE_RECRUITER);
        long totalCompanies = companyRepository.count();
        long totalJobs = jobRepository.count();
        long activeJobs = jobRepository.countByStatus(JobStatus.PUBLISHED);
        long totalApplications = applicationRepository.count();

        Map<String, Long> statusCounts = new LinkedHashMap<>();
        for (ApplicationStatus status : ApplicationStatus.values()) {
            statusCounts.put(status.name(), applicationRepository.countByStatus(status));
        }

        Pageable topFive = PageRequest.of(0, 5, Sort.by("createdAt").descending());
        List<UserDto> recentUsers = userRepository.findAll(topFive)
                .map(EntityMapper::toUserDto)
                .getContent();

        List<JobDto> recentJobs = jobRepository.findAll(PageRequest.of(0, 5, Sort.by("postedDate").descending()))
                .map(j -> EntityMapper.toJobDto(j, (long) applicationRepository.findByJobOrderByAppliedAtDesc(j).size(), false))
                .getContent();

        return AdminDashboardDto.builder()
                .totalUsers(totalUsers)
                .totalCandidates(totalCandidates)
                .totalRecruiters(totalRecruiters)
                .totalCompanies(totalCompanies)
                .totalJobs(totalJobs)
                .activeJobs(activeJobs)
                .totalApplications(totalApplications)
                .applicationsByStatus(statusCounts)
                .recentUsers(recentUsers)
                .recentJobs(recentJobs)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return userRepository.findAllByOrderByCreatedAtDesc(pageable).map(EntityMapper::toUserDto);
    }

    @Override
    @Transactional
    public UserDto toggleUserStatus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setStatus(user.getStatus() == UserStatus.ACTIVE ? UserStatus.INACTIVE : UserStatus.ACTIVE);
        User saved = userRepository.save(user);
        return EntityMapper.toUserDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobDto> getAllJobs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("postedDate").descending());
        return jobRepository.findAllByOrderByPostedDateDesc(pageable)
                .map(j -> EntityMapper.toJobDto(j, (long) applicationRepository.findByJobOrderByAppliedAtDesc(j).size(), false));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationDto> getAllApplications(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("appliedAt").descending());
        return applicationRepository.findAllByOrderByAppliedAtDesc(pageable).map(EntityMapper::toApplicationDto);
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateDashboardDto getCandidateDashboard(Long candidateId) {
        User candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", candidateId));

        long totalApps = applicationRepository.countByCandidate(candidate);
        long underReviewApps = applicationRepository.countByCandidateAndStatus(candidate, ApplicationStatus.UNDER_REVIEW);
        long shortlistedApps = applicationRepository.countByCandidateAndStatus(candidate, ApplicationStatus.SHORTLISTED);
        long scheduledInterviews = interviewRepository.countByCandidateAndStatus(candidate, InterviewStatus.SCHEDULED);

        CandidateProfile profile = candidateProfileRepository.findByUser(candidate).orElse(null);
        int percentage = 20;
        if (profile != null) {
            if (profile.getHeadline() != null && !profile.getHeadline().trim().isEmpty()) percentage += 15;
            if (profile.getBio() != null && !profile.getBio().trim().isEmpty()) percentage += 15;
            if (profile.getSkills() != null && !profile.getSkills().trim().isEmpty()) percentage += 20;
            if (profile.getEducationJson() != null && !profile.getEducationJson().trim().isEmpty()) percentage += 10;
            if (profile.getActiveResume() != null) percentage += 20;
        }

        List<ApplicationDto> recentApplications = applicationRepository.findByCandidateOrderByAppliedAtDesc(candidate)
                .stream()
                .limit(5)
                .map(EntityMapper::toApplicationDto)
                .collect(Collectors.toList());

        List<InterviewDto> upcomingInterviews = interviewRepository.findByCandidateAndInterviewDateGreaterThanEqualOrderByInterviewDateAsc(
                candidate, LocalDate.now())
                .stream()
                .limit(4)
                .map(EntityMapper::toInterviewDto)
                .collect(Collectors.toList());

        List<JobDto> recommendedJobs = jobRepository.searchJobs(JobStatus.PUBLISHED, null, null, null, PageRequest.of(0, 4, Sort.by("postedDate").descending()))
                .map(j -> EntityMapper.toJobDto(j, (long) applicationRepository.findByJobOrderByAppliedAtDesc(j).size(), applicationRepository.existsByJobIdAndCandidateId(j.getId(), candidateId)))
                .getContent();

        return CandidateDashboardDto.builder()
                .totalApplications(totalApps)
                .underReviewApplications(underReviewApps)
                .shortlistedApplications(shortlistedApps)
                .scheduledInterviews(scheduledInterviews)
                .profileCompletionPercentage(Math.min(percentage, 100))
                .recentApplications(recentApplications)
                .upcomingInterviews(upcomingInterviews)
                .recommendedJobs(recommendedJobs)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public RecruiterDashboardDto getRecruiterDashboard(Long recruiterId) {
        User recruiter = userRepository.findById(recruiterId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", recruiterId));

        long totalJobs = jobRepository.countByRecruiter(recruiter);
        long activeJobs = jobRepository.countByRecruiterAndStatus(recruiter, JobStatus.PUBLISHED);
        long totalApplicants = applicationRepository.countByRecruiter(recruiter);
        long shortlistedCandidates = applicationRepository.countByRecruiterAndStatus(recruiter, ApplicationStatus.SHORTLISTED);
        long scheduledInterviews = interviewRepository.countByRecruiterAndStatus(recruiter, InterviewStatus.SCHEDULED);

        List<JobDto> recentJobs = jobRepository.findByRecruiterOrderByPostedDateDesc(recruiter)
                .stream()
                .limit(4)
                .map(j -> EntityMapper.toJobDto(j, (long) applicationRepository.findByJobOrderByAppliedAtDesc(j).size(), false))
                .collect(Collectors.toList());

        List<ApplicationDto> recentApplications = applicationRepository.findByRecruiter(recruiter)
                .stream()
                .limit(5)
                .map(EntityMapper::toApplicationDto)
                .collect(Collectors.toList());

        List<InterviewDto> upcomingInterviews = interviewRepository.findByRecruiterAndInterviewDateGreaterThanEqualOrderByInterviewDateAsc(
                recruiter, LocalDate.now())
                .stream()
                .limit(4)
                .map(EntityMapper::toInterviewDto)
                .collect(Collectors.toList());

        Map<String, Long> statusCounts = new LinkedHashMap<>();
        for (ApplicationStatus status : ApplicationStatus.values()) {
            statusCounts.put(status.name(), applicationRepository.countByRecruiterAndStatus(recruiter, status));
        }

        return RecruiterDashboardDto.builder()
                .totalJobs(totalJobs)
                .activeJobs(activeJobs)
                .totalApplicants(totalApplicants)
                .shortlistedCandidates(shortlistedCandidates)
                .scheduledInterviews(scheduledInterviews)
                .recentJobs(recentJobs)
                .recentApplications(recentApplications)
                .upcomingInterviews(upcomingInterviews)
                .applicationsByStatus(statusCounts)
                .build();
    }
}

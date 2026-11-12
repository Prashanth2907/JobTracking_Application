package com.jobtrack.mapper;

import com.jobtrack.dto.response.*;
import com.jobtrack.entity.*;

public final class EntityMapper {

    private EntityMapper() {}

    public static UserDto toUserDto(User user) {
        if (user == null) return null;
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .role(user.getRole())
                .status(user.getStatus())
                .profileImageUrl(user.getProfileImageUrl())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public static CompanyDto toCompanyDto(Company company) {
        if (company == null) return null;
        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .website(company.getWebsite())
                .industry(company.getIndustry())
                .companySize(company.getCompanySize())
                .logoUrl(company.getLogoUrl())
                .address(company.getAddress())
                .createdAt(company.getCreatedAt())
                .build();
    }

    public static ResumeDto toResumeDto(Resume resume) {
        if (resume == null) return null;
        return ResumeDto.builder()
                .id(resume.getId())
                .candidateId(resume.getCandidate() != null ? resume.getCandidate().getId() : null)
                .candidateName(resume.getCandidate() != null ? resume.getCandidate().getFullName() : null)
                .originalFilename(resume.getOriginalFilename())
                .fileType(resume.getFileType())
                .fileSize(resume.getFileSize())
                .uploadedAt(resume.getUploadedAt())
                .build();
    }

    public static CandidateProfileDto toCandidateProfileDto(CandidateProfile profile) {
        if (profile == null) return null;
        return CandidateProfileDto.builder()
                .id(profile.getId())
                .user(toUserDto(profile.getUser()))
                .headline(profile.getHeadline())
                .bio(profile.getBio())
                .location(profile.getLocation())
                .currentSalary(profile.getCurrentSalary())
                .expectedSalary(profile.getExpectedSalary())
                .experienceYears(profile.getExperienceYears())
                .skills(profile.getSkills())
                .educationJson(profile.getEducationJson())
                .experienceJson(profile.getExperienceJson())
                .activeResume(toResumeDto(profile.getActiveResume()))
                .updatedAt(profile.getUpdatedAt())
                .build();
    }

    public static RecruiterProfileDto toRecruiterProfileDto(RecruiterProfile profile) {
        if (profile == null) return null;
        return RecruiterProfileDto.builder()
                .id(profile.getId())
                .user(toUserDto(profile.getUser()))
                .company(toCompanyDto(profile.getCompany()))
                .designation(profile.getDesignation())
                .department(profile.getDepartment())
                .contactPhone(profile.getContactPhone())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }

    public static JobDto toJobDto(Job job, Long applicantCount, Boolean hasApplied) {
        if (job == null) return null;
        return JobDto.builder()
                .id(job.getId())
                .recruiterId(job.getRecruiter() != null ? job.getRecruiter().getId() : null)
                .recruiterName(job.getRecruiter() != null ? job.getRecruiter().getFullName() : null)
                .companyId(job.getCompany() != null ? job.getCompany().getId() : null)
                .companyName(job.getCompanyName())
                .companyLogoUrl(job.getCompany() != null ? job.getCompany().getLogoUrl() : null)
                .title(job.getTitle())
                .description(job.getDescription())
                .requirements(job.getRequirements())
                .skillsRequired(job.getSkillsRequired())
                .location(job.getLocation())
                .employmentType(job.getEmploymentType())
                .experienceRequired(job.getExperienceRequired())
                .salaryMin(job.getSalaryMin())
                .salaryMax(job.getSalaryMax())
                .vacanciesCount(job.getVacanciesCount())
                .deadline(job.getDeadline())
                .status(job.getStatus())
                .postedDate(job.getPostedDate())
                .applicantCount(applicantCount != null ? applicantCount : 0L)
                .hasApplied(hasApplied != null ? hasApplied : false)
                .build();
    }

    public static ApplicationDto toApplicationDto(Application app) {
        if (app == null) return null;
        return ApplicationDto.builder()
                .id(app.getId())
                .jobId(app.getJob() != null ? app.getJob().getId() : null)
                .jobTitle(app.getJob() != null ? app.getJob().getTitle() : null)
                .companyName(app.getJob() != null ? app.getJob().getCompanyName() : null)
                .jobLocation(app.getJob() != null ? app.getJob().getLocation() : null)
                .candidateId(app.getCandidate() != null ? app.getCandidate().getId() : null)
                .candidateName(app.getCandidate() != null ? app.getCandidate().getFullName() : null)
                .candidateEmail(app.getCandidate() != null ? app.getCandidate().getEmail() : null)
                .candidatePhone(app.getCandidate() != null ? app.getCandidate().getPhone() : null)
                .resume(toResumeDto(app.getResume()))
                .coverLetter(app.getCoverLetter())
                .status(app.getStatus())
                .appliedAt(app.getAppliedAt())
                .updatedAt(app.getUpdatedAt())
                .build();
    }

    public static InterviewDto toInterviewDto(Interview interview) {
        if (interview == null) return null;
        return InterviewDto.builder()
                .id(interview.getId())
                .applicationId(interview.getApplication() != null ? interview.getApplication().getId() : null)
                .jobId(interview.getApplication() != null && interview.getApplication().getJob() != null ? interview.getApplication().getJob().getId() : null)
                .jobTitle(interview.getApplication() != null && interview.getApplication().getJob() != null ? interview.getApplication().getJob().getTitle() : null)
                .companyName(interview.getApplication() != null && interview.getApplication().getJob() != null ? interview.getApplication().getJob().getCompanyName() : null)
                .candidateId(interview.getCandidate() != null ? interview.getCandidate().getId() : null)
                .candidateName(interview.getCandidate() != null ? interview.getCandidate().getFullName() : null)
                .candidateEmail(interview.getCandidate() != null ? interview.getCandidate().getEmail() : null)
                .recruiterId(interview.getRecruiter() != null ? interview.getRecruiter().getId() : null)
                .recruiterName(interview.getRecruiter() != null ? interview.getRecruiter().getFullName() : null)
                .interviewDate(interview.getInterviewDate())
                .interviewTime(interview.getInterviewTime())
                .interviewType(interview.getInterviewType())
                .meetingLink(interview.getMeetingLink())
                .locationAddress(interview.getLocationAddress())
                .instructions(interview.getInstructions())
                .status(interview.getStatus())
                .createdAt(interview.getCreatedAt())
                .build();
    }

    public static NotificationDto toNotificationDto(Notification n) {
        if (n == null) return null;
        return NotificationDto.builder()
                .id(n.getId())
                .title(n.getTitle())
                .message(n.getMessage())
                .type(n.getType())
                .isRead(n.getIsRead())
                .linkUrl(n.getLinkUrl())
                .createdAt(n.getCreatedAt())
                .build();
    }
}

package com.jobtrack.dto.response;

import com.jobtrack.entity.EmploymentType;
import com.jobtrack.entity.JobStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JobDto {
    private Long id;
    private Long recruiterId;
    private String recruiterName;
    private Long companyId;
    private String companyName;
    private String companyLogoUrl;
    private String title;
    private String description;
    private String requirements;
    private String skillsRequired;
    private String location;
    private EmploymentType employmentType;
    private String experienceRequired;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private Integer vacanciesCount;
    private LocalDate deadline;
    private JobStatus status;
    private LocalDateTime postedDate;
    private Long applicantCount;
    private Boolean hasApplied;

    public JobDto() {}

    public JobDto(Long id, Long recruiterId, String recruiterName, Long companyId, String companyName, String companyLogoUrl, String title, String description, String requirements, String skillsRequired, String location, EmploymentType employmentType, String experienceRequired, BigDecimal salaryMin, BigDecimal salaryMax, Integer vacanciesCount, LocalDate deadline, JobStatus status, LocalDateTime postedDate, Long applicantCount, Boolean hasApplied) {
        this.id = id;
        this.recruiterId = recruiterId;
        this.recruiterName = recruiterName;
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyLogoUrl = companyLogoUrl;
        this.title = title;
        this.description = description;
        this.requirements = requirements;
        this.skillsRequired = skillsRequired;
        this.location = location;
        this.employmentType = employmentType;
        this.experienceRequired = experienceRequired;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.vacanciesCount = vacanciesCount;
        this.deadline = deadline;
        this.status = status;
        this.postedDate = postedDate;
        this.applicantCount = applicantCount;
        this.hasApplied = hasApplied;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long recruiterId;
        private String recruiterName;
        private Long companyId;
        private String companyName;
        private String companyLogoUrl;
        private String title;
        private String description;
        private String requirements;
        private String skillsRequired;
        private String location;
        private EmploymentType employmentType;
        private String experienceRequired;
        private BigDecimal salaryMin;
        private BigDecimal salaryMax;
        private Integer vacanciesCount;
        private LocalDate deadline;
        private JobStatus status;
        private LocalDateTime postedDate;
        private Long applicantCount;
        private Boolean hasApplied;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder recruiterId(Long recruiterId) { this.recruiterId = recruiterId; return this; }
        public Builder recruiterName(String recruiterName) { this.recruiterName = recruiterName; return this; }
        public Builder companyId(Long companyId) { this.companyId = companyId; return this; }
        public Builder companyName(String companyName) { this.companyName = companyName; return this; }
        public Builder companyLogoUrl(String companyLogoUrl) { this.companyLogoUrl = companyLogoUrl; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder requirements(String requirements) { this.requirements = requirements; return this; }
        public Builder skillsRequired(String skillsRequired) { this.skillsRequired = skillsRequired; return this; }
        public Builder location(String location) { this.location = location; return this; }
        public Builder employmentType(EmploymentType employmentType) { this.employmentType = employmentType; return this; }
        public Builder experienceRequired(String experienceRequired) { this.experienceRequired = experienceRequired; return this; }
        public Builder salaryMin(BigDecimal salaryMin) { this.salaryMin = salaryMin; return this; }
        public Builder salaryMax(BigDecimal salaryMax) { this.salaryMax = salaryMax; return this; }
        public Builder vacanciesCount(Integer vacanciesCount) { this.vacanciesCount = vacanciesCount; return this; }
        public Builder deadline(LocalDate deadline) { this.deadline = deadline; return this; }
        public Builder status(JobStatus status) { this.status = status; return this; }
        public Builder postedDate(LocalDateTime postedDate) { this.postedDate = postedDate; return this; }
        public Builder applicantCount(Long applicantCount) { this.applicantCount = applicantCount; return this; }
        public Builder hasApplied(Boolean hasApplied) { this.hasApplied = hasApplied; return this; }

        public JobDto build() {
            return new JobDto(id, recruiterId, recruiterName, companyId, companyName, companyLogoUrl, title, description, requirements, skillsRequired, location, employmentType, experienceRequired, salaryMin, salaryMax, vacanciesCount, deadline, status, postedDate, applicantCount, hasApplied);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getRecruiterId() { return recruiterId; }
    public void setRecruiterId(Long recruiterId) { this.recruiterId = recruiterId; }
    public String getRecruiterName() { return recruiterName; }
    public void setRecruiterName(String recruiterName) { this.recruiterName = recruiterName; }
    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getCompanyLogoUrl() { return companyLogoUrl; }
    public void setCompanyLogoUrl(String companyLogoUrl) { this.companyLogoUrl = companyLogoUrl; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getRequirements() { return requirements; }
    public void setRequirements(String requirements) { this.requirements = requirements; }
    public String getSkillsRequired() { return skillsRequired; }
    public void setSkillsRequired(String skillsRequired) { this.skillsRequired = skillsRequired; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public EmploymentType getEmploymentType() { return employmentType; }
    public void setEmploymentType(EmploymentType employmentType) { this.employmentType = employmentType; }
    public String getExperienceRequired() { return experienceRequired; }
    public void setExperienceRequired(String experienceRequired) { this.experienceRequired = experienceRequired; }
    public BigDecimal getSalaryMin() { return salaryMin; }
    public void setSalaryMin(BigDecimal salaryMin) { this.salaryMin = salaryMin; }
    public BigDecimal getSalaryMax() { return salaryMax; }
    public void setSalaryMax(BigDecimal salaryMax) { this.salaryMax = salaryMax; }
    public Integer getVacanciesCount() { return vacanciesCount; }
    public void setVacanciesCount(Integer vacanciesCount) { this.vacanciesCount = vacanciesCount; }
    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public JobStatus getStatus() { return status; }
    public void setStatus(JobStatus status) { this.status = status; }
    public LocalDateTime getPostedDate() { return postedDate; }
    public void setPostedDate(LocalDateTime postedDate) { this.postedDate = postedDate; }
    public Long getApplicantCount() { return applicantCount; }
    public void setApplicantCount(Long applicantCount) { this.applicantCount = applicantCount; }
    public Boolean getHasApplied() { return hasApplied; }
    public void setHasApplied(Boolean hasApplied) { this.hasApplied = hasApplied; }
}

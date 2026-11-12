package com.jobtrack.dto.request;

import com.jobtrack.entity.EmploymentType;
import com.jobtrack.entity.JobStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class JobUpdateRequest {
    private String title;
    private String companyName;
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

    public JobUpdateRequest() {}

    public JobUpdateRequest(String title, String companyName, String description, String requirements, String skillsRequired, String location, EmploymentType employmentType, String experienceRequired, BigDecimal salaryMin, BigDecimal salaryMax, Integer vacanciesCount, LocalDate deadline, JobStatus status) {
        this.title = title;
        this.companyName = companyName;
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
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String title;
        private String companyName;
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

        public Builder title(String title) { this.title = title; return this; }
        public Builder companyName(String companyName) { this.companyName = companyName; return this; }
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

        public JobUpdateRequest build() {
            return new JobUpdateRequest(title, companyName, description, requirements, skillsRequired, location, employmentType, experienceRequired, salaryMin, salaryMax, vacanciesCount, deadline, status);
        }
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
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
}

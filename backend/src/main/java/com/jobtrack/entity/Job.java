package com.jobtrack.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id", nullable = false)
    private User recruiter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(name = "company_name", nullable = false, length = 150)
    private String companyName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String requirements;

    @Column(name = "skills_required", nullable = false, length = 500)
    private String skillsRequired;

    @Column(nullable = false, length = 100)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type", nullable = false, length = 50)
    private EmploymentType employmentType = EmploymentType.FULL_TIME;

    @Column(name = "experience_required", length = 50)
    private String experienceRequired;

    @Column(name = "salary_min", precision = 12, scale = 2)
    private BigDecimal salaryMin;

    @Column(name = "salary_max", precision = 12, scale = 2)
    private BigDecimal salaryMax;

    @Column(name = "vacancies_count")
    private Integer vacanciesCount = 1;

    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private JobStatus status = JobStatus.PUBLISHED;

    @CreationTimestamp
    @Column(name = "posted_date", updatable = false)
    private LocalDateTime postedDate;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Job() {}

    public Job(Long id, User recruiter, Company company, String title, String companyName, String description, String requirements, String skillsRequired, String location, EmploymentType employmentType, String experienceRequired, BigDecimal salaryMin, BigDecimal salaryMax, Integer vacanciesCount, LocalDate deadline, JobStatus status, LocalDateTime postedDate, LocalDateTime updatedAt) {
        this.id = id;
        this.recruiter = recruiter;
        this.company = company;
        this.title = title;
        this.companyName = companyName;
        this.description = description;
        this.requirements = requirements;
        this.skillsRequired = skillsRequired;
        this.location = location;
        this.employmentType = employmentType != null ? employmentType : EmploymentType.FULL_TIME;
        this.experienceRequired = experienceRequired;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.vacanciesCount = vacanciesCount != null ? vacanciesCount : 1;
        this.deadline = deadline;
        this.status = status != null ? status : JobStatus.PUBLISHED;
        this.postedDate = postedDate;
        this.updatedAt = updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private User recruiter;
        private Company company;
        private String title;
        private String companyName;
        private String description;
        private String requirements;
        private String skillsRequired;
        private String location;
        private EmploymentType employmentType = EmploymentType.FULL_TIME;
        private String experienceRequired;
        private BigDecimal salaryMin;
        private BigDecimal salaryMax;
        private Integer vacanciesCount = 1;
        private LocalDate deadline;
        private JobStatus status = JobStatus.PUBLISHED;
        private LocalDateTime postedDate;
        private LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder recruiter(User recruiter) { this.recruiter = recruiter; return this; }
        public Builder company(Company company) { this.company = company; return this; }
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
        public Builder postedDate(LocalDateTime postedDate) { this.postedDate = postedDate; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Job build() {
            return new Job(id, recruiter, company, title, companyName, description, requirements, skillsRequired, location, employmentType, experienceRequired, salaryMin, salaryMax, vacanciesCount, deadline, status, postedDate, updatedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getRecruiter() { return recruiter; }
    public void setRecruiter(User recruiter) { this.recruiter = recruiter; }
    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }
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
    public LocalDateTime getPostedDate() { return postedDate; }
    public void setPostedDate(LocalDateTime postedDate) { this.postedDate = postedDate; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

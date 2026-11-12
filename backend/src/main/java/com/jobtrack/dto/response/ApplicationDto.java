package com.jobtrack.dto.response;

import com.jobtrack.entity.ApplicationStatus;

import java.time.LocalDateTime;

public class ApplicationDto {
    private Long id;
    private Long jobId;
    private String jobTitle;
    private String companyName;
    private String jobLocation;
    private Long candidateId;
    private String candidateName;
    private String candidateEmail;
    private String candidatePhone;
    private ResumeDto resume;
    private String coverLetter;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;

    public ApplicationDto() {}

    public ApplicationDto(Long id, Long jobId, String jobTitle, String companyName, String jobLocation, Long candidateId, String candidateName, String candidateEmail, String candidatePhone, ResumeDto resume, String coverLetter, ApplicationStatus status, LocalDateTime appliedAt, LocalDateTime updatedAt) {
        this.id = id;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.jobLocation = jobLocation;
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.candidateEmail = candidateEmail;
        this.candidatePhone = candidatePhone;
        this.resume = resume;
        this.coverLetter = coverLetter;
        this.status = status;
        this.appliedAt = appliedAt;
        this.updatedAt = updatedAt;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long jobId;
        private String jobTitle;
        private String companyName;
        private String jobLocation;
        private Long candidateId;
        private String candidateName;
        private String candidateEmail;
        private String candidatePhone;
        private ResumeDto resume;
        private String coverLetter;
        private ApplicationStatus status;
        private LocalDateTime appliedAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder jobId(Long jobId) { this.jobId = jobId; return this; }
        public Builder jobTitle(String jobTitle) { this.jobTitle = jobTitle; return this; }
        public Builder companyName(String companyName) { this.companyName = companyName; return this; }
        public Builder jobLocation(String jobLocation) { this.jobLocation = jobLocation; return this; }
        public Builder candidateId(Long candidateId) { this.candidateId = candidateId; return this; }
        public Builder candidateName(String candidateName) { this.candidateName = candidateName; return this; }
        public Builder candidateEmail(String candidateEmail) { this.candidateEmail = candidateEmail; return this; }
        public Builder candidatePhone(String candidatePhone) { this.candidatePhone = candidatePhone; return this; }
        public Builder resume(ResumeDto resume) { this.resume = resume; return this; }
        public Builder coverLetter(String coverLetter) { this.coverLetter = coverLetter; return this; }
        public Builder status(ApplicationStatus status) { this.status = status; return this; }
        public Builder appliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public ApplicationDto build() {
            return new ApplicationDto(id, jobId, jobTitle, companyName, jobLocation, candidateId, candidateName, candidateEmail, candidatePhone, resume, coverLetter, status, appliedAt, updatedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getJobLocation() { return jobLocation; }
    public void setJobLocation(String jobLocation) { this.jobLocation = jobLocation; }
    public Long getCandidateId() { return candidateId; }
    public void setCandidateId(Long candidateId) { this.candidateId = candidateId; }
    public String getCandidateName() { return candidateName; }
    public void setCandidateName(String candidateName) { this.candidateName = candidateName; }
    public String getCandidateEmail() { return candidateEmail; }
    public void setCandidateEmail(String candidateEmail) { this.candidateEmail = candidateEmail; }
    public String getCandidatePhone() { return candidatePhone; }
    public void setCandidatePhone(String candidatePhone) { this.candidatePhone = candidatePhone; }
    public ResumeDto getResume() { return resume; }
    public void setResume(ResumeDto resume) { this.resume = resume; }
    public String getCoverLetter() { return coverLetter; }
    public void setCoverLetter(String coverLetter) { this.coverLetter = coverLetter; }
    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
    public LocalDateTime getAppliedAt() { return appliedAt; }
    public void setAppliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

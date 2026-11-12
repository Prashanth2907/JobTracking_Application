package com.jobtrack.dto.response;

import com.jobtrack.entity.InterviewStatus;
import com.jobtrack.entity.InterviewType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class InterviewDto {
    private Long id;
    private Long applicationId;
    private Long jobId;
    private String jobTitle;
    private String companyName;
    private Long candidateId;
    private String candidateName;
    private String candidateEmail;
    private Long recruiterId;
    private String recruiterName;
    private LocalDate interviewDate;
    private LocalTime interviewTime;
    private InterviewType interviewType;
    private String meetingLink;
    private String locationAddress;
    private String instructions;
    private InterviewStatus status;
    private LocalDateTime createdAt;

    public InterviewDto() {}

    public InterviewDto(Long id, Long applicationId, Long jobId, String jobTitle, String companyName, Long candidateId, String candidateName, String candidateEmail, Long recruiterId, String recruiterName, LocalDate interviewDate, LocalTime interviewTime, InterviewType interviewType, String meetingLink, String locationAddress, String instructions, InterviewStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.applicationId = applicationId;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.candidateEmail = candidateEmail;
        this.recruiterId = recruiterId;
        this.recruiterName = recruiterName;
        this.interviewDate = interviewDate;
        this.interviewTime = interviewTime;
        this.interviewType = interviewType;
        this.meetingLink = meetingLink;
        this.locationAddress = locationAddress;
        this.instructions = instructions;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long applicationId;
        private Long jobId;
        private String jobTitle;
        private String companyName;
        private Long candidateId;
        private String candidateName;
        private String candidateEmail;
        private Long recruiterId;
        private String recruiterName;
        private LocalDate interviewDate;
        private LocalTime interviewTime;
        private InterviewType interviewType;
        private String meetingLink;
        private String locationAddress;
        private String instructions;
        private InterviewStatus status;
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder applicationId(Long applicationId) { this.applicationId = applicationId; return this; }
        public Builder jobId(Long jobId) { this.jobId = jobId; return this; }
        public Builder jobTitle(String jobTitle) { this.jobTitle = jobTitle; return this; }
        public Builder companyName(String companyName) { this.companyName = companyName; return this; }
        public Builder candidateId(Long candidateId) { this.candidateId = candidateId; return this; }
        public Builder candidateName(String candidateName) { this.candidateName = candidateName; return this; }
        public Builder candidateEmail(String candidateEmail) { this.candidateEmail = candidateEmail; return this; }
        public Builder recruiterId(Long recruiterId) { this.recruiterId = recruiterId; return this; }
        public Builder recruiterName(String recruiterName) { this.recruiterName = recruiterName; return this; }
        public Builder interviewDate(LocalDate interviewDate) { this.interviewDate = interviewDate; return this; }
        public Builder interviewTime(LocalTime interviewTime) { this.interviewTime = interviewTime; return this; }
        public Builder interviewType(InterviewType interviewType) { this.interviewType = interviewType; return this; }
        public Builder meetingLink(String meetingLink) { this.meetingLink = meetingLink; return this; }
        public Builder locationAddress(String locationAddress) { this.locationAddress = locationAddress; return this; }
        public Builder instructions(String instructions) { this.instructions = instructions; return this; }
        public Builder status(InterviewStatus status) { this.status = status; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public InterviewDto build() {
            return new InterviewDto(id, applicationId, jobId, jobTitle, companyName, candidateId, candidateName, candidateEmail, recruiterId, recruiterName, interviewDate, interviewTime, interviewType, meetingLink, locationAddress, instructions, status, createdAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public Long getCandidateId() { return candidateId; }
    public void setCandidateId(Long candidateId) { this.candidateId = candidateId; }
    public String getCandidateName() { return candidateName; }
    public void setCandidateName(String candidateName) { this.candidateName = candidateName; }
    public String getCandidateEmail() { return candidateEmail; }
    public void setCandidateEmail(String candidateEmail) { this.candidateEmail = candidateEmail; }
    public Long getRecruiterId() { return recruiterId; }
    public void setRecruiterId(Long recruiterId) { this.recruiterId = recruiterId; }
    public String getRecruiterName() { return recruiterName; }
    public void setRecruiterName(String recruiterName) { this.recruiterName = recruiterName; }
    public LocalDate getInterviewDate() { return interviewDate; }
    public void setInterviewDate(LocalDate interviewDate) { this.interviewDate = interviewDate; }
    public LocalTime getInterviewTime() { return interviewTime; }
    public void setInterviewTime(LocalTime interviewTime) { this.interviewTime = interviewTime; }
    public InterviewType getInterviewType() { return interviewType; }
    public void setInterviewType(InterviewType interviewType) { this.interviewType = interviewType; }
    public String getMeetingLink() { return meetingLink; }
    public void setMeetingLink(String meetingLink) { this.meetingLink = meetingLink; }
    public String getLocationAddress() { return locationAddress; }
    public void setLocationAddress(String locationAddress) { this.locationAddress = locationAddress; }
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public InterviewStatus getStatus() { return status; }
    public void setStatus(InterviewStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

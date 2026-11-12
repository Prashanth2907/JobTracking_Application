package com.jobtrack.dto.request;

import com.jobtrack.entity.InterviewType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class InterviewScheduleRequest {

    @NotNull(message = "Application ID is required")
    private Long applicationId;

    @NotNull(message = "Interview date is required")
    @FutureOrPresent(message = "Interview date cannot be in the past")
    private LocalDate interviewDate;

    @NotNull(message = "Interview time is required")
    private LocalTime interviewTime;

    @NotNull(message = "Interview type is required (ONLINE or IN_PERSON)")
    private InterviewType interviewType;

    private String meetingLink;
    private String locationAddress;
    private String instructions;

    public InterviewScheduleRequest() {}

    public InterviewScheduleRequest(Long applicationId, LocalDate interviewDate, LocalTime interviewTime, InterviewType interviewType, String meetingLink, String locationAddress, String instructions) {
        this.applicationId = applicationId;
        this.interviewDate = interviewDate;
        this.interviewTime = interviewTime;
        this.interviewType = interviewType;
        this.meetingLink = meetingLink;
        this.locationAddress = locationAddress;
        this.instructions = instructions;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long applicationId;
        private LocalDate interviewDate;
        private LocalTime interviewTime;
        private InterviewType interviewType;
        private String meetingLink;
        private String locationAddress;
        private String instructions;

        public Builder applicationId(Long applicationId) { this.applicationId = applicationId; return this; }
        public Builder interviewDate(LocalDate interviewDate) { this.interviewDate = interviewDate; return this; }
        public Builder interviewTime(LocalTime interviewTime) { this.interviewTime = interviewTime; return this; }
        public Builder interviewType(InterviewType interviewType) { this.interviewType = interviewType; return this; }
        public Builder meetingLink(String meetingLink) { this.meetingLink = meetingLink; return this; }
        public Builder locationAddress(String locationAddress) { this.locationAddress = locationAddress; return this; }
        public Builder instructions(String instructions) { this.instructions = instructions; return this; }

        public InterviewScheduleRequest build() {
            return new InterviewScheduleRequest(applicationId, interviewDate, interviewTime, interviewType, meetingLink, locationAddress, instructions);
        }
    }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
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
}

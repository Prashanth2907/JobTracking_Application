package com.jobtrack.dto.request;

import java.math.BigDecimal;

public class CandidateProfileUpdateRequest {
    private String fullName;
    private String phone;
    private String headline;
    private String bio;
    private String location;
    private BigDecimal currentSalary;
    private BigDecimal expectedSalary;
    private Integer experienceYears;
    private String skills;
    private String educationJson;
    private String experienceJson;
    private Long activeResumeId;

    public CandidateProfileUpdateRequest() {}

    public CandidateProfileUpdateRequest(String fullName, String phone, String headline, String bio, String location, BigDecimal currentSalary, BigDecimal expectedSalary, Integer experienceYears, String skills, String educationJson, String experienceJson, Long activeResumeId) {
        this.fullName = fullName;
        this.phone = phone;
        this.headline = headline;
        this.bio = bio;
        this.location = location;
        this.currentSalary = currentSalary;
        this.expectedSalary = expectedSalary;
        this.experienceYears = experienceYears;
        this.skills = skills;
        this.educationJson = educationJson;
        this.experienceJson = experienceJson;
        this.activeResumeId = activeResumeId;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String fullName;
        private String phone;
        private String headline;
        private String bio;
        private String location;
        private BigDecimal currentSalary;
        private BigDecimal expectedSalary;
        private Integer experienceYears;
        private String skills;
        private String educationJson;
        private String experienceJson;
        private Long activeResumeId;

        public Builder fullName(String fullName) { this.fullName = fullName; return this; }
        public Builder phone(String phone) { this.phone = phone; return this; }
        public Builder headline(String headline) { this.headline = headline; return this; }
        public Builder bio(String bio) { this.bio = bio; return this; }
        public Builder location(String location) { this.location = location; return this; }
        public Builder currentSalary(BigDecimal currentSalary) { this.currentSalary = currentSalary; return this; }
        public Builder expectedSalary(BigDecimal expectedSalary) { this.expectedSalary = expectedSalary; return this; }
        public Builder experienceYears(Integer experienceYears) { this.experienceYears = experienceYears; return this; }
        public Builder skills(String skills) { this.skills = skills; return this; }
        public Builder educationJson(String educationJson) { this.educationJson = educationJson; return this; }
        public Builder experienceJson(String experienceJson) { this.experienceJson = experienceJson; return this; }
        public Builder activeResumeId(Long activeResumeId) { this.activeResumeId = activeResumeId; return this; }

        public CandidateProfileUpdateRequest build() {
            return new CandidateProfileUpdateRequest(fullName, phone, headline, bio, location, currentSalary, expectedSalary, experienceYears, skills, educationJson, experienceJson, activeResumeId);
        }
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getHeadline() { return headline; }
    public void setHeadline(String headline) { this.headline = headline; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public BigDecimal getCurrentSalary() { return currentSalary; }
    public void setCurrentSalary(BigDecimal currentSalary) { this.currentSalary = currentSalary; }
    public BigDecimal getExpectedSalary() { return expectedSalary; }
    public void setExpectedSalary(BigDecimal expectedSalary) { this.expectedSalary = expectedSalary; }
    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }
    public String getEducationJson() { return educationJson; }
    public void setEducationJson(String educationJson) { this.educationJson = educationJson; }
    public String getExperienceJson() { return experienceJson; }
    public void setExperienceJson(String experienceJson) { this.experienceJson = experienceJson; }
    public Long getActiveResumeId() { return activeResumeId; }
    public void setActiveResumeId(Long activeResumeId) { this.activeResumeId = activeResumeId; }
}

package com.jobtrack.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CandidateProfileDto {
    private Long id;
    private UserDto user;
    private String headline;
    private String bio;
    private String location;
    private BigDecimal currentSalary;
    private BigDecimal expectedSalary;
    private Integer experienceYears;
    private String skills;
    private String educationJson;
    private String experienceJson;
    private ResumeDto activeResume;
    private LocalDateTime updatedAt;

    public CandidateProfileDto() {}

    public CandidateProfileDto(Long id, UserDto user, String headline, String bio, String location, BigDecimal currentSalary, BigDecimal expectedSalary, Integer experienceYears, String skills, String educationJson, String experienceJson, ResumeDto activeResume, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.headline = headline;
        this.bio = bio;
        this.location = location;
        this.currentSalary = currentSalary;
        this.expectedSalary = expectedSalary;
        this.experienceYears = experienceYears;
        this.skills = skills;
        this.educationJson = educationJson;
        this.experienceJson = experienceJson;
        this.activeResume = activeResume;
        this.updatedAt = updatedAt;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private UserDto user;
        private String headline;
        private String bio;
        private String location;
        private BigDecimal currentSalary;
        private BigDecimal expectedSalary;
        private Integer experienceYears;
        private String skills;
        private String educationJson;
        private String experienceJson;
        private ResumeDto activeResume;
        private LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder user(UserDto user) { this.user = user; return this; }
        public Builder headline(String headline) { this.headline = headline; return this; }
        public Builder bio(String bio) { this.bio = bio; return this; }
        public Builder location(String location) { this.location = location; return this; }
        public Builder currentSalary(BigDecimal currentSalary) { this.currentSalary = currentSalary; return this; }
        public Builder expectedSalary(BigDecimal expectedSalary) { this.expectedSalary = expectedSalary; return this; }
        public Builder experienceYears(Integer experienceYears) { this.experienceYears = experienceYears; return this; }
        public Builder skills(String skills) { this.skills = skills; return this; }
        public Builder educationJson(String educationJson) { this.educationJson = educationJson; return this; }
        public Builder experienceJson(String experienceJson) { this.experienceJson = experienceJson; return this; }
        public Builder activeResume(ResumeDto activeResume) { this.activeResume = activeResume; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public CandidateProfileDto build() {
            return new CandidateProfileDto(id, user, headline, bio, location, currentSalary, expectedSalary, experienceYears, skills, educationJson, experienceJson, activeResume, updatedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }
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
    public ResumeDto getActiveResume() { return activeResume; }
    public void setActiveResume(ResumeDto activeResume) { this.activeResume = activeResume; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

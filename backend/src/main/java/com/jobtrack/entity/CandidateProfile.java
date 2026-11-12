package com.jobtrack.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_profiles")
public class CandidateProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(length = 200)
    private String headline;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(length = 100)
    private String location;

    @Column(name = "current_salary", precision = 12, scale = 2)
    private BigDecimal currentSalary;

    @Column(name = "expected_salary", precision = 12, scale = 2)
    private BigDecimal expectedSalary;

    @Column(name = "experience_years")
    private Integer experienceYears = 0;

    @Column(columnDefinition = "TEXT")
    private String skills;

    @Column(name = "education_json", columnDefinition = "TEXT")
    private String educationJson;

    @Column(name = "experience_json", columnDefinition = "TEXT")
    private String experienceJson;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "active_resume_id")
    private Resume activeResume;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public CandidateProfile() {}

    public CandidateProfile(Long id, User user, String headline, String bio, String location, BigDecimal currentSalary, BigDecimal expectedSalary, Integer experienceYears, String skills, String educationJson, String experienceJson, Resume activeResume, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.headline = headline;
        this.bio = bio;
        this.location = location;
        this.currentSalary = currentSalary;
        this.expectedSalary = expectedSalary;
        this.experienceYears = experienceYears != null ? experienceYears : 0;
        this.skills = skills;
        this.educationJson = educationJson;
        this.experienceJson = experienceJson;
        this.activeResume = activeResume;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private User user;
        private String headline;
        private String bio;
        private String location;
        private BigDecimal currentSalary;
        private BigDecimal expectedSalary;
        private Integer experienceYears = 0;
        private String skills;
        private String educationJson;
        private String experienceJson;
        private Resume activeResume;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder user(User user) { this.user = user; return this; }
        public Builder headline(String headline) { this.headline = headline; return this; }
        public Builder bio(String bio) { this.bio = bio; return this; }
        public Builder location(String location) { this.location = location; return this; }
        public Builder currentSalary(BigDecimal currentSalary) { this.currentSalary = currentSalary; return this; }
        public Builder expectedSalary(BigDecimal expectedSalary) { this.expectedSalary = expectedSalary; return this; }
        public Builder experienceYears(Integer experienceYears) { this.experienceYears = experienceYears; return this; }
        public Builder skills(String skills) { this.skills = skills; return this; }
        public Builder educationJson(String educationJson) { this.educationJson = educationJson; return this; }
        public Builder experienceJson(String experienceJson) { this.experienceJson = experienceJson; return this; }
        public Builder activeResume(Resume activeResume) { this.activeResume = activeResume; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public CandidateProfile build() {
            return new CandidateProfile(id, user, headline, bio, location, currentSalary, expectedSalary, experienceYears, skills, educationJson, experienceJson, activeResume, createdAt, updatedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
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
    public Resume getActiveResume() { return activeResume; }
    public void setActiveResume(Resume activeResume) { this.activeResume = activeResume; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

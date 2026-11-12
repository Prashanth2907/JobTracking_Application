package com.jobtrack.dto.response;

import java.util.List;
import java.util.Map;

public class AdminDashboardDto {
    private long totalUsers;
    private long totalCandidates;
    private long totalRecruiters;
    private long totalCompanies;
    private long totalJobs;
    private long activeJobs;
    private long totalApplications;
    private Map<String, Long> applicationsByStatus;
    private List<UserDto> recentUsers;
    private List<JobDto> recentJobs;

    public AdminDashboardDto() {}

    public AdminDashboardDto(long totalUsers, long totalCandidates, long totalRecruiters, long totalCompanies, long totalJobs, long activeJobs, long totalApplications, Map<String, Long> applicationsByStatus, List<UserDto> recentUsers, List<JobDto> recentJobs) {
        this.totalUsers = totalUsers;
        this.totalCandidates = totalCandidates;
        this.totalRecruiters = totalRecruiters;
        this.totalCompanies = totalCompanies;
        this.totalJobs = totalJobs;
        this.activeJobs = activeJobs;
        this.totalApplications = totalApplications;
        this.applicationsByStatus = applicationsByStatus;
        this.recentUsers = recentUsers;
        this.recentJobs = recentJobs;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private long totalUsers;
        private long totalCandidates;
        private long totalRecruiters;
        private long totalCompanies;
        private long totalJobs;
        private long activeJobs;
        private long totalApplications;
        private Map<String, Long> applicationsByStatus;
        private List<UserDto> recentUsers;
        private List<JobDto> recentJobs;

        public Builder totalUsers(long totalUsers) { this.totalUsers = totalUsers; return this; }
        public Builder totalCandidates(long totalCandidates) { this.totalCandidates = totalCandidates; return this; }
        public Builder totalRecruiters(long totalRecruiters) { this.totalRecruiters = totalRecruiters; return this; }
        public Builder totalCompanies(long totalCompanies) { this.totalCompanies = totalCompanies; return this; }
        public Builder totalJobs(long totalJobs) { this.totalJobs = totalJobs; return this; }
        public Builder activeJobs(long activeJobs) { this.activeJobs = activeJobs; return this; }
        public Builder totalApplications(long totalApplications) { this.totalApplications = totalApplications; return this; }
        public Builder applicationsByStatus(Map<String, Long> applicationsByStatus) { this.applicationsByStatus = applicationsByStatus; return this; }
        public Builder recentUsers(List<UserDto> recentUsers) { this.recentUsers = recentUsers; return this; }
        public Builder recentJobs(List<JobDto> recentJobs) { this.recentJobs = recentJobs; return this; }

        public AdminDashboardDto build() {
            return new AdminDashboardDto(totalUsers, totalCandidates, totalRecruiters, totalCompanies, totalJobs, activeJobs, totalApplications, applicationsByStatus, recentUsers, recentJobs);
        }
    }

    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }
    public long getTotalCandidates() { return totalCandidates; }
    public void setTotalCandidates(long totalCandidates) { this.totalCandidates = totalCandidates; }
    public long getTotalRecruiters() { return totalRecruiters; }
    public void setTotalRecruiters(long totalRecruiters) { this.totalRecruiters = totalRecruiters; }
    public long getTotalCompanies() { return totalCompanies; }
    public void setTotalCompanies(long totalCompanies) { this.totalCompanies = totalCompanies; }
    public long getTotalJobs() { return totalJobs; }
    public void setTotalJobs(long totalJobs) { this.totalJobs = totalJobs; }
    public long getActiveJobs() { return activeJobs; }
    public void setActiveJobs(long activeJobs) { this.activeJobs = activeJobs; }
    public long getTotalApplications() { return totalApplications; }
    public void setTotalApplications(long totalApplications) { this.totalApplications = totalApplications; }
    public Map<String, Long> getApplicationsByStatus() { return applicationsByStatus; }
    public void setApplicationsByStatus(Map<String, Long> applicationsByStatus) { this.applicationsByStatus = applicationsByStatus; }
    public List<UserDto> getRecentUsers() { return recentUsers; }
    public void setRecentUsers(List<UserDto> recentUsers) { this.recentUsers = recentUsers; }
    public List<JobDto> getRecentJobs() { return recentJobs; }
    public void setRecentJobs(List<JobDto> recentJobs) { this.recentJobs = recentJobs; }
}

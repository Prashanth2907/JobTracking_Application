package com.jobtrack.dto.response;

import java.util.List;
import java.util.Map;

public class RecruiterDashboardDto {
    private long totalJobs;
    private long activeJobs;
    private long totalApplicants;
    private long shortlistedCandidates;
    private long scheduledInterviews;
    private List<JobDto> recentJobs;
    private List<ApplicationDto> recentApplications;
    private List<InterviewDto> upcomingInterviews;
    private Map<String, Long> applicationsByStatus;

    public RecruiterDashboardDto() {}

    public RecruiterDashboardDto(long totalJobs, long activeJobs, long totalApplicants, long shortlistedCandidates, long scheduledInterviews, List<JobDto> recentJobs, List<ApplicationDto> recentApplications, List<InterviewDto> upcomingInterviews, Map<String, Long> applicationsByStatus) {
        this.totalJobs = totalJobs;
        this.activeJobs = activeJobs;
        this.totalApplicants = totalApplicants;
        this.shortlistedCandidates = shortlistedCandidates;
        this.scheduledInterviews = scheduledInterviews;
        this.recentJobs = recentJobs;
        this.recentApplications = recentApplications;
        this.upcomingInterviews = upcomingInterviews;
        this.applicationsByStatus = applicationsByStatus;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private long totalJobs;
        private long activeJobs;
        private long totalApplicants;
        private long shortlistedCandidates;
        private long scheduledInterviews;
        private List<JobDto> recentJobs;
        private List<ApplicationDto> recentApplications;
        private List<InterviewDto> upcomingInterviews;
        private Map<String, Long> applicationsByStatus;

        public Builder totalJobs(long totalJobs) { this.totalJobs = totalJobs; return this; }
        public Builder activeJobs(long activeJobs) { this.activeJobs = activeJobs; return this; }
        public Builder totalApplicants(long totalApplicants) { this.totalApplicants = totalApplicants; return this; }
        public Builder shortlistedCandidates(long shortlistedCandidates) { this.shortlistedCandidates = shortlistedCandidates; return this; }
        public Builder scheduledInterviews(long scheduledInterviews) { this.scheduledInterviews = scheduledInterviews; return this; }
        public Builder recentJobs(List<JobDto> recentJobs) { this.recentJobs = recentJobs; return this; }
        public Builder recentApplications(List<ApplicationDto> recentApplications) { this.recentApplications = recentApplications; return this; }
        public Builder upcomingInterviews(List<InterviewDto> upcomingInterviews) { this.upcomingInterviews = upcomingInterviews; return this; }
        public Builder applicationsByStatus(Map<String, Long> applicationsByStatus) { this.applicationsByStatus = applicationsByStatus; return this; }

        public RecruiterDashboardDto build() {
            return new RecruiterDashboardDto(totalJobs, activeJobs, totalApplicants, shortlistedCandidates, scheduledInterviews, recentJobs, recentApplications, upcomingInterviews, applicationsByStatus);
        }
    }

    public long getTotalJobs() { return totalJobs; }
    public void setTotalJobs(long totalJobs) { this.totalJobs = totalJobs; }
    public long getActiveJobs() { return activeJobs; }
    public void setActiveJobs(long activeJobs) { this.activeJobs = activeJobs; }
    public long getTotalApplicants() { return totalApplicants; }
    public void setTotalApplicants(long totalApplicants) { this.totalApplicants = totalApplicants; }
    public long getShortlistedCandidates() { return shortlistedCandidates; }
    public void setShortlistedCandidates(long shortlistedCandidates) { this.shortlistedCandidates = shortlistedCandidates; }
    public long getScheduledInterviews() { return scheduledInterviews; }
    public void setScheduledInterviews(long scheduledInterviews) { this.scheduledInterviews = scheduledInterviews; }
    public List<JobDto> getRecentJobs() { return recentJobs; }
    public void setRecentJobs(List<JobDto> recentJobs) { this.recentJobs = recentJobs; }
    public List<ApplicationDto> getRecentApplications() { return recentApplications; }
    public void setRecentApplications(List<ApplicationDto> recentApplications) { this.recentApplications = recentApplications; }
    public List<InterviewDto> getUpcomingInterviews() { return upcomingInterviews; }
    public void setUpcomingInterviews(List<InterviewDto> upcomingInterviews) { this.upcomingInterviews = upcomingInterviews; }
    public Map<String, Long> getApplicationsByStatus() { return applicationsByStatus; }
    public void setApplicationsByStatus(Map<String, Long> applicationsByStatus) { this.applicationsByStatus = applicationsByStatus; }
}

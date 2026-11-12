package com.jobtrack.dto.response;

import java.util.List;

public class CandidateDashboardDto {
    private long totalApplications;
    private long underReviewApplications;
    private long shortlistedApplications;
    private long scheduledInterviews;
    private int profileCompletionPercentage;
    private List<ApplicationDto> recentApplications;
    private List<InterviewDto> upcomingInterviews;
    private List<JobDto> recommendedJobs;

    public CandidateDashboardDto() {}

    public CandidateDashboardDto(long totalApplications, long underReviewApplications, long shortlistedApplications, long scheduledInterviews, int profileCompletionPercentage, List<ApplicationDto> recentApplications, List<InterviewDto> upcomingInterviews, List<JobDto> recommendedJobs) {
        this.totalApplications = totalApplications;
        this.underReviewApplications = underReviewApplications;
        this.shortlistedApplications = shortlistedApplications;
        this.scheduledInterviews = scheduledInterviews;
        this.profileCompletionPercentage = profileCompletionPercentage;
        this.recentApplications = recentApplications;
        this.upcomingInterviews = upcomingInterviews;
        this.recommendedJobs = recommendedJobs;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private long totalApplications;
        private long underReviewApplications;
        private long shortlistedApplications;
        private long scheduledInterviews;
        private int profileCompletionPercentage;
        private List<ApplicationDto> recentApplications;
        private List<InterviewDto> upcomingInterviews;
        private List<JobDto> recommendedJobs;

        public Builder totalApplications(long totalApplications) { this.totalApplications = totalApplications; return this; }
        public Builder underReviewApplications(long underReviewApplications) { this.underReviewApplications = underReviewApplications; return this; }
        public Builder shortlistedApplications(long shortlistedApplications) { this.shortlistedApplications = shortlistedApplications; return this; }
        public Builder scheduledInterviews(long scheduledInterviews) { this.scheduledInterviews = scheduledInterviews; return this; }
        public Builder profileCompletionPercentage(int profileCompletionPercentage) { this.profileCompletionPercentage = profileCompletionPercentage; return this; }
        public Builder recentApplications(List<ApplicationDto> recentApplications) { this.recentApplications = recentApplications; return this; }
        public Builder upcomingInterviews(List<InterviewDto> upcomingInterviews) { this.upcomingInterviews = upcomingInterviews; return this; }
        public Builder recommendedJobs(List<JobDto> recommendedJobs) { this.recommendedJobs = recommendedJobs; return this; }

        public CandidateDashboardDto build() {
            return new CandidateDashboardDto(totalApplications, underReviewApplications, shortlistedApplications, scheduledInterviews, profileCompletionPercentage, recentApplications, upcomingInterviews, recommendedJobs);
        }
    }

    public long getTotalApplications() { return totalApplications; }
    public void setTotalApplications(long totalApplications) { this.totalApplications = totalApplications; }
    public long getUnderReviewApplications() { return underReviewApplications; }
    public void setUnderReviewApplications(long underReviewApplications) { this.underReviewApplications = underReviewApplications; }
    public long getShortlistedApplications() { return shortlistedApplications; }
    public void setShortlistedApplications(long shortlistedApplications) { this.shortlistedApplications = shortlistedApplications; }
    public long getScheduledInterviews() { return scheduledInterviews; }
    public void setScheduledInterviews(long scheduledInterviews) { this.scheduledInterviews = scheduledInterviews; }
    public int getProfileCompletionPercentage() { return profileCompletionPercentage; }
    public void setProfileCompletionPercentage(int profileCompletionPercentage) { this.profileCompletionPercentage = profileCompletionPercentage; }
    public List<ApplicationDto> getRecentApplications() { return recentApplications; }
    public void setRecentApplications(List<ApplicationDto> recentApplications) { this.recentApplications = recentApplications; }
    public List<InterviewDto> getUpcomingInterviews() { return upcomingInterviews; }
    public void setUpcomingInterviews(List<InterviewDto> upcomingInterviews) { this.upcomingInterviews = upcomingInterviews; }
    public List<JobDto> getRecommendedJobs() { return recommendedJobs; }
    public void setRecommendedJobs(List<JobDto> recommendedJobs) { this.recommendedJobs = recommendedJobs; }
}

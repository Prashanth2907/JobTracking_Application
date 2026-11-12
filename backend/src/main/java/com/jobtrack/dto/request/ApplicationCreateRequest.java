package com.jobtrack.dto.request;

import jakarta.validation.constraints.NotNull;

public class ApplicationCreateRequest {

    @NotNull(message = "Job ID is required")
    private Long jobId;

    private Long resumeId;

    private String coverLetter;

    public ApplicationCreateRequest() {}

    public ApplicationCreateRequest(Long jobId, Long resumeId, String coverLetter) {
        this.jobId = jobId;
        this.resumeId = resumeId;
        this.coverLetter = coverLetter;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long jobId;
        private Long resumeId;
        private String coverLetter;

        public Builder jobId(Long jobId) { this.jobId = jobId; return this; }
        public Builder resumeId(Long resumeId) { this.resumeId = resumeId; return this; }
        public Builder coverLetter(String coverLetter) { this.coverLetter = coverLetter; return this; }

        public ApplicationCreateRequest build() {
            return new ApplicationCreateRequest(jobId, resumeId, coverLetter);
        }
    }

    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }
    public Long getResumeId() { return resumeId; }
    public void setResumeId(Long resumeId) { this.resumeId = resumeId; }
    public String getCoverLetter() { return coverLetter; }
    public void setCoverLetter(String coverLetter) { this.coverLetter = coverLetter; }
}

package com.jobtrack.dto.request;

import com.jobtrack.entity.ApplicationStatus;
import jakarta.validation.constraints.NotNull;

public class ApplicationStatusUpdateRequest {

    @NotNull(message = "Status is required")
    private ApplicationStatus status;

    private String note;

    public ApplicationStatusUpdateRequest() {}

    public ApplicationStatusUpdateRequest(ApplicationStatus status, String note) {
        this.status = status;
        this.note = note;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private ApplicationStatus status;
        private String note;

        public Builder status(ApplicationStatus status) { this.status = status; return this; }
        public Builder note(String note) { this.note = note; return this; }

        public ApplicationStatusUpdateRequest build() {
            return new ApplicationStatusUpdateRequest(status, note);
        }
    }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}

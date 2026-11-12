package com.jobtrack.dto.response;

import java.time.LocalDateTime;

public class RecruiterProfileDto {
    private Long id;
    private UserDto user;
    private CompanyDto company;
    private String designation;
    private String department;
    private String contactPhone;
    private LocalDateTime updatedAt;

    public RecruiterProfileDto() {}

    public RecruiterProfileDto(Long id, UserDto user, CompanyDto company, String designation, String department, String contactPhone, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.company = company;
        this.designation = designation;
        this.department = department;
        this.contactPhone = contactPhone;
        this.updatedAt = updatedAt;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private UserDto user;
        private CompanyDto company;
        private String designation;
        private String department;
        private String contactPhone;
        private LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder user(UserDto user) { this.user = user; return this; }
        public Builder company(CompanyDto company) { this.company = company; return this; }
        public Builder designation(String designation) { this.designation = designation; return this; }
        public Builder department(String department) { this.department = department; return this; }
        public Builder contactPhone(String contactPhone) { this.contactPhone = contactPhone; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public RecruiterProfileDto build() {
            return new RecruiterProfileDto(id, user, company, designation, department, contactPhone, updatedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }
    public CompanyDto getCompany() { return company; }
    public void setCompany(CompanyDto company) { this.company = company; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

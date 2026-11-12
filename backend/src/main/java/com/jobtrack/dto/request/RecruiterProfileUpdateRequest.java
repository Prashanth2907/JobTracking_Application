package com.jobtrack.dto.request;

public class RecruiterProfileUpdateRequest {
    private String fullName;
    private String phone;
    private String designation;
    private String department;
    private String contactPhone;

    private String companyName;
    private String companyDescription;
    private String companyWebsite;
    private String companyIndustry;
    private String companySize;
    private String companyLogoUrl;
    private String companyAddress;

    public RecruiterProfileUpdateRequest() {}

    public RecruiterProfileUpdateRequest(String fullName, String phone, String designation, String department, String contactPhone, String companyName, String companyDescription, String companyWebsite, String companyIndustry, String companySize, String companyLogoUrl, String companyAddress) {
        this.fullName = fullName;
        this.phone = phone;
        this.designation = designation;
        this.department = department;
        this.contactPhone = contactPhone;
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.companyWebsite = companyWebsite;
        this.companyIndustry = companyIndustry;
        this.companySize = companySize;
        this.companyLogoUrl = companyLogoUrl;
        this.companyAddress = companyAddress;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String fullName;
        private String phone;
        private String designation;
        private String department;
        private String contactPhone;
        private String companyName;
        private String companyDescription;
        private String companyWebsite;
        private String companyIndustry;
        private String companySize;
        private String companyLogoUrl;
        private String companyAddress;

        public Builder fullName(String fullName) { this.fullName = fullName; return this; }
        public Builder phone(String phone) { this.phone = phone; return this; }
        public Builder designation(String designation) { this.designation = designation; return this; }
        public Builder department(String department) { this.department = department; return this; }
        public Builder contactPhone(String contactPhone) { this.contactPhone = contactPhone; return this; }
        public Builder companyName(String companyName) { this.companyName = companyName; return this; }
        public Builder companyDescription(String companyDescription) { this.companyDescription = companyDescription; return this; }
        public Builder companyWebsite(String companyWebsite) { this.companyWebsite = companyWebsite; return this; }
        public Builder companyIndustry(String companyIndustry) { this.companyIndustry = companyIndustry; return this; }
        public Builder companySize(String companySize) { this.companySize = companySize; return this; }
        public Builder companyLogoUrl(String companyLogoUrl) { this.companyLogoUrl = companyLogoUrl; return this; }
        public Builder companyAddress(String companyAddress) { this.companyAddress = companyAddress; return this; }

        public RecruiterProfileUpdateRequest build() {
            return new RecruiterProfileUpdateRequest(fullName, phone, designation, department, contactPhone, companyName, companyDescription, companyWebsite, companyIndustry, companySize, companyLogoUrl, companyAddress);
        }
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getCompanyDescription() { return companyDescription; }
    public void setCompanyDescription(String companyDescription) { this.companyDescription = companyDescription; }
    public String getCompanyWebsite() { return companyWebsite; }
    public void setCompanyWebsite(String companyWebsite) { this.companyWebsite = companyWebsite; }
    public String getCompanyIndustry() { return companyIndustry; }
    public void setCompanyIndustry(String companyIndustry) { this.companyIndustry = companyIndustry; }
    public String getCompanySize() { return companySize; }
    public void setCompanySize(String companySize) { this.companySize = companySize; }
    public String getCompanyLogoUrl() { return companyLogoUrl; }
    public void setCompanyLogoUrl(String companyLogoUrl) { this.companyLogoUrl = companyLogoUrl; }
    public String getCompanyAddress() { return companyAddress; }
    public void setCompanyAddress(String companyAddress) { this.companyAddress = companyAddress; }
}

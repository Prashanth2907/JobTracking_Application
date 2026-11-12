package com.jobtrack.dto.response;

import java.time.LocalDateTime;

public class CompanyDto {
    private Long id;
    private String name;
    private String description;
    private String website;
    private String industry;
    private String companySize;
    private String logoUrl;
    private String address;
    private LocalDateTime createdAt;

    public CompanyDto() {}

    public CompanyDto(Long id, String name, String description, String website, String industry, String companySize, String logoUrl, String address, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.website = website;
        this.industry = industry;
        this.companySize = companySize;
        this.logoUrl = logoUrl;
        this.address = address;
        this.createdAt = createdAt;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private String website;
        private String industry;
        private String companySize;
        private String logoUrl;
        private String address;
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder website(String website) { this.website = website; return this; }
        public Builder industry(String industry) { this.industry = industry; return this; }
        public Builder companySize(String companySize) { this.companySize = companySize; return this; }
        public Builder logoUrl(String logoUrl) { this.logoUrl = logoUrl; return this; }
        public Builder address(String address) { this.address = address; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public CompanyDto build() {
            return new CompanyDto(id, name, description, website, industry, companySize, logoUrl, address, createdAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }
    public String getCompanySize() { return companySize; }
    public void setCompanySize(String companySize) { this.companySize = companySize; }
    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

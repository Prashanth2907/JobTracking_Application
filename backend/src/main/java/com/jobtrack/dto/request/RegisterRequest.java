package com.jobtrack.dto.request;

import com.jobtrack.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Full name is required")
    private String fullName;

    private String phone;

    @NotNull(message = "Role is required (ROLE_CANDIDATE or ROLE_RECRUITER)")
    private Role role;

    private String companyName;
    private String designation;

    public RegisterRequest() {}

    public RegisterRequest(String email, String password, String fullName, String phone, Role role, String companyName, String designation) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.role = role;
        this.companyName = companyName;
        this.designation = designation;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String email;
        private String password;
        private String fullName;
        private String phone;
        private Role role;
        private String companyName;
        private String designation;

        public Builder email(String email) { this.email = email; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder fullName(String fullName) { this.fullName = fullName; return this; }
        public Builder phone(String phone) { this.phone = phone; return this; }
        public Builder role(Role role) { this.role = role; return this; }
        public Builder companyName(String companyName) { this.companyName = companyName; return this; }
        public Builder designation(String designation) { this.designation = designation; return this; }

        public RegisterRequest build() {
            return new RegisterRequest(email, password, fullName, phone, role, companyName, designation);
        }
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
}

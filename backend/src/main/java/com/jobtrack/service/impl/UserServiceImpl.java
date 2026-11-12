package com.jobtrack.service.impl;

import com.jobtrack.dto.request.CandidateProfileUpdateRequest;
import com.jobtrack.dto.request.RecruiterProfileUpdateRequest;
import com.jobtrack.dto.response.CandidateProfileDto;
import com.jobtrack.dto.response.RecruiterProfileDto;
import com.jobtrack.dto.response.UserDto;
import com.jobtrack.entity.*;
import com.jobtrack.exception.ResourceNotFoundException;
import com.jobtrack.mapper.EntityMapper;
import com.jobtrack.repository.*;
import com.jobtrack.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final CompanyRepository companyRepository;
    private final ResumeRepository resumeRepository;

    public UserServiceImpl(UserRepository userRepository, CandidateProfileRepository candidateProfileRepository, RecruiterProfileRepository recruiterProfileRepository, CompanyRepository companyRepository, ResumeRepository resumeRepository) {
        this.userRepository = userRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.companyRepository = companyRepository;
        this.resumeRepository = resumeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateProfileDto getCandidateProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        CandidateProfile profile = candidateProfileRepository.findByUser(user)
                .orElseGet(() -> candidateProfileRepository.save(
                        CandidateProfile.builder().user(user).experienceYears(0).skills("").build()
                ));

        return EntityMapper.toCandidateProfileDto(profile);
    }

    @Override
    @Transactional
    public CandidateProfileDto updateCandidateProfile(Long userId, CandidateProfileUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (request.getFullName() != null && !request.getFullName().trim().isEmpty()) {
            user.setFullName(request.getFullName().trim());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone().trim());
        }
        userRepository.save(user);

        CandidateProfile profile = candidateProfileRepository.findByUser(user)
                .orElseGet(() -> CandidateProfile.builder().user(user).build());

        if (request.getHeadline() != null) profile.setHeadline(request.getHeadline());
        if (request.getBio() != null) profile.setBio(request.getBio());
        if (request.getLocation() != null) profile.setLocation(request.getLocation());
        if (request.getCurrentSalary() != null) profile.setCurrentSalary(request.getCurrentSalary());
        if (request.getExpectedSalary() != null) profile.setExpectedSalary(request.getExpectedSalary());
        if (request.getExperienceYears() != null) profile.setExperienceYears(request.getExperienceYears());
        if (request.getSkills() != null) profile.setSkills(request.getSkills());
        if (request.getEducationJson() != null) profile.setEducationJson(request.getEducationJson());
        if (request.getExperienceJson() != null) profile.setExperienceJson(request.getExperienceJson());

        if (request.getActiveResumeId() != null) {
            Resume resume = resumeRepository.findByIdAndCandidate(request.getActiveResumeId(), user)
                    .orElse(null);
            profile.setActiveResume(resume);
        }

        CandidateProfile saved = candidateProfileRepository.save(profile);
        return EntityMapper.toCandidateProfileDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public RecruiterProfileDto getRecruiterProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        RecruiterProfile profile = recruiterProfileRepository.findByUser(user)
                .orElseGet(() -> recruiterProfileRepository.save(
                        RecruiterProfile.builder().user(user).build()
                ));

        return EntityMapper.toRecruiterProfileDto(profile);
    }

    @Override
    @Transactional
    public RecruiterProfileDto updateRecruiterProfile(Long userId, RecruiterProfileUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (request.getFullName() != null && !request.getFullName().trim().isEmpty()) {
            user.setFullName(request.getFullName().trim());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone().trim());
        }
        userRepository.save(user);

        RecruiterProfile profile = recruiterProfileRepository.findByUser(user)
                .orElseGet(() -> RecruiterProfile.builder().user(user).build());

        if (request.getDesignation() != null) profile.setDesignation(request.getDesignation());
        if (request.getDepartment() != null) profile.setDepartment(request.getDepartment());
        if (request.getContactPhone() != null) profile.setContactPhone(request.getContactPhone());

        Company company = profile.getCompany();
        if (company == null && request.getCompanyName() != null && !request.getCompanyName().trim().isEmpty()) {
            company = companyRepository.findByName(request.getCompanyName().trim())
                    .orElseGet(() -> Company.builder().name(request.getCompanyName().trim()).build());
        }

        if (company != null) {
            if (request.getCompanyName() != null && !request.getCompanyName().trim().isEmpty()) {
                company.setName(request.getCompanyName().trim());
            }
            if (request.getCompanyDescription() != null) company.setDescription(request.getCompanyDescription());
            if (request.getCompanyWebsite() != null) company.setWebsite(request.getCompanyWebsite());
            if (request.getCompanyIndustry() != null) company.setIndustry(request.getCompanyIndustry());
            if (request.getCompanySize() != null) company.setCompanySize(request.getCompanySize());
            if (request.getCompanyLogoUrl() != null) company.setLogoUrl(request.getCompanyLogoUrl());
            if (request.getCompanyAddress() != null) company.setAddress(request.getCompanyAddress());

            Company savedCompany = companyRepository.save(company);
            profile.setCompany(savedCompany);
        }

        RecruiterProfile saved = recruiterProfileRepository.save(profile);
        return EntityMapper.toRecruiterProfileDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return EntityMapper.toUserDto(user);
    }
}

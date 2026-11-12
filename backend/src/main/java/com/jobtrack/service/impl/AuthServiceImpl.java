package com.jobtrack.service.impl;

import com.jobtrack.dto.request.LoginRequest;
import com.jobtrack.dto.request.RegisterRequest;
import com.jobtrack.dto.response.AuthResponse;
import com.jobtrack.dto.response.UserDto;
import com.jobtrack.entity.*;
import com.jobtrack.exception.BadRequestException;
import com.jobtrack.exception.ResourceNotFoundException;
import com.jobtrack.mapper.EntityMapper;
import com.jobtrack.repository.CandidateProfileRepository;
import com.jobtrack.repository.CompanyRepository;
import com.jobtrack.repository.RecruiterProfileRepository;
import com.jobtrack.repository.UserRepository;
import com.jobtrack.security.JwtUtil;
import com.jobtrack.security.SecurityUtils;
import com.jobtrack.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, CandidateProfileRepository candidateProfileRepository, RecruiterProfileRepository recruiterProfileRepository, CompanyRepository companyRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail().trim().toLowerCase())) {
            throw new BadRequestException("An account with email " + request.getEmail() + " already exists");
        }

        if (request.getRole() == Role.ROLE_ADMIN) {
            throw new BadRequestException("Admin registration is not allowed via public registration endpoint");
        }

        User user = User.builder()
                .email(request.getEmail().trim().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName().trim())
                .phone(request.getPhone())
                .role(request.getRole())
                .status(UserStatus.ACTIVE)
                .build();

        User savedUser = userRepository.save(user);

        if (savedUser.getRole() == Role.ROLE_CANDIDATE) {
            CandidateProfile candidateProfile = CandidateProfile.builder()
                    .user(savedUser)
                    .experienceYears(0)
                    .skills("")
                    .build();
            candidateProfileRepository.save(candidateProfile);
        } else if (savedUser.getRole() == Role.ROLE_RECRUITER) {
            Company company = null;
            if (request.getCompanyName() != null && !request.getCompanyName().trim().isEmpty()) {
                String compName = request.getCompanyName().trim();
                company = companyRepository.findByName(compName)
                        .orElseGet(() -> companyRepository.save(Company.builder().name(compName).build()));
            }
            RecruiterProfile recruiterProfile = RecruiterProfile.builder()
                    .user(savedUser)
                    .company(company)
                    .designation(request.getDesignation())
                    .contactPhone(request.getPhone())
                    .build();
            recruiterProfileRepository.save(recruiterProfile);
        }

        String token = jwtUtil.generateTokenFromEmail(
                savedUser.getEmail(),
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getRole().name()
        );

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .user(EntityMapper.toUserDto(savedUser))
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail().trim().toLowerCase(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(request.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new BadRequestException("Your account is currently inactive or deactivated. Please contact support.");
        }

        String token = jwtUtil.generateToken(authentication);

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .user(EntityMapper.toUserDto(user))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getCurrentUser() {
        Long userId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return EntityMapper.toUserDto(user);
    }
}

package com.jobtrack.service;

import com.jobtrack.dto.request.LoginRequest;
import com.jobtrack.dto.request.RegisterRequest;
import com.jobtrack.dto.response.AuthResponse;
import com.jobtrack.entity.Role;
import com.jobtrack.entity.User;
import com.jobtrack.entity.UserStatus;
import com.jobtrack.exception.BadRequestException;
import com.jobtrack.repository.CandidateProfileRepository;
import com.jobtrack.repository.CompanyRepository;
import com.jobtrack.repository.RecruiterProfileRepository;
import com.jobtrack.repository.UserRepository;
import com.jobtrack.security.JwtUtil;
import com.jobtrack.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CandidateProfileRepository candidateProfileRepository;

    @Mock
    private RecruiterProfileRepository recruiterProfileRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    private User sampleCandidate;

    @BeforeEach
    void setUp() {
        sampleCandidate = User.builder()
                .id(1L)
                .email("test.candidate@example.com")
                .password("encoded_pass")
                .fullName("Test Candidate")
                .role(Role.ROLE_CANDIDATE)
                .status(UserStatus.ACTIVE)
                .build();
    }

    @Test
    @DisplayName("Should successfully register candidate")
    void registerCandidate_Success() {
        RegisterRequest request = RegisterRequest.builder()
                .email("test.candidate@example.com")
                .password("Password@123")
                .fullName("Test Candidate")
                .role(Role.ROLE_CANDIDATE)
                .build();

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_pass");
        when(userRepository.save(any(User.class))).thenReturn(sampleCandidate);
        when(jwtUtil.generateTokenFromEmail(anyString(), anyLong(), anyString(), anyString())).thenReturn("mock_token");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("mock_token", response.getToken());
        assertEquals("test.candidate@example.com", response.getUser().getEmail());
        verify(candidateProfileRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should throw BadRequestException if email already registered")
    void registerCandidate_DuplicateEmail() {
        RegisterRequest request = RegisterRequest.builder()
                .email("test.candidate@example.com")
                .password("Password@123")
                .fullName("Test Candidate")
                .role(Role.ROLE_CANDIDATE)
                .build();

        when(userRepository.existsByEmail("test.candidate@example.com")).thenReturn(true);

        assertThrows(BadRequestException.class, () -> authService.register(request));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should successfully authenticate user and return token")
    void login_Success() {
        LoginRequest request = LoginRequest.builder()
                .email("test.candidate@example.com")
                .password("Password@123")
                .build();

        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(userRepository.findByEmail("test.candidate@example.com")).thenReturn(Optional.of(sampleCandidate));
        when(jwtUtil.generateToken(auth)).thenReturn("jwt_token_123");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwt_token_123", response.getToken());
        assertEquals("test.candidate@example.com", response.getUser().getEmail());
    }
}

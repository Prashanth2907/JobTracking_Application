package com.jobtrack.service.impl;

import com.jobtrack.dto.response.ResumeDto;
import com.jobtrack.entity.CandidateProfile;
import com.jobtrack.entity.Resume;
import com.jobtrack.entity.Role;
import com.jobtrack.entity.User;
import com.jobtrack.exception.BadRequestException;
import com.jobtrack.exception.ResourceNotFoundException;
import com.jobtrack.exception.UnauthorizedException;
import com.jobtrack.mapper.EntityMapper;
import com.jobtrack.repository.CandidateProfileRepository;
import com.jobtrack.repository.ResumeRepository;
import com.jobtrack.repository.UserRepository;
import com.jobtrack.service.ResumeStorageService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResumeStorageServiceImpl implements ResumeStorageService {

    private static final Logger log = LoggerFactory.getLogger(ResumeStorageServiceImpl.class);

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final CandidateProfileRepository candidateProfileRepository;

    @Value("${file.upload-dir:./uploads/resumes}")
    private String uploadDir;

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".pdf", ".doc", ".docx");
    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/octet-stream"
    );

    public ResumeStorageServiceImpl(ResumeRepository resumeRepository, UserRepository userRepository, CandidateProfileRepository candidateProfileRepository) {
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
        this.candidateProfileRepository = candidateProfileRepository;
    }

    @PostConstruct
    public void init() {
        try {
            Path path = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(path);
        } catch (IOException e) {
            log.error("Could not initialize storage directory: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResumeDto uploadResume(Long candidateId, MultipartFile file) {
        User candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", candidateId));

        if (file.isEmpty()) {
            throw new BadRequestException("Uploaded file cannot be empty");
        }

        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (originalFilename.contains("..")) {
            throw new BadRequestException("Filename contains invalid sequence: " + originalFilename);
        }

        String extension = "";
        int i = originalFilename.lastIndexOf('.');
        if (i > 0) {
            extension = originalFilename.substring(i).toLowerCase();
        }

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new BadRequestException("Unsupported file type. Please upload a PDF or DOC/DOCX file.");
        }

        String contentType = file.getContentType();
        if (contentType != null && !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            log.warn("Uploaded file has unrecognized contentType: {}", contentType);
        }

        try {
            Path targetLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(targetLocation);

            String safeName = UUID.randomUUID().toString() + "_" + originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_");
            Path targetPath = targetLocation.resolve(safeName);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            Resume resume = Resume.builder()
                    .candidate(candidate)
                    .originalFilename(originalFilename)
                    .storedFilename(safeName)
                    .fileType(file.getContentType() != null ? file.getContentType() : "application/pdf")
                    .fileSize(file.getSize())
                    .storagePath(targetPath.toString())
                    .build();

            Resume saved = resumeRepository.save(resume);

            CandidateProfile profile = candidateProfileRepository.findByUser(candidate).orElse(null);
            if (profile != null) {
                profile.setActiveResume(saved);
                candidateProfileRepository.save(profile);
            }

            return EntityMapper.toResumeDto(saved);
        } catch (IOException ex) {
            throw new BadRequestException("Could not store file: " + ex.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Resource loadResumeAsResource(Long resumeId, Long currentUserId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume", "id", resumeId));

        if (currentUserId != null) {
            User currentUser = userRepository.findById(currentUserId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUserId));

            boolean isOwner = resume.getCandidate().getId().equals(currentUserId);
            boolean isRecruiterOrAdmin = currentUser.getRole() == Role.ROLE_RECRUITER || currentUser.getRole() == Role.ROLE_ADMIN;

            if (!isOwner && !isRecruiterOrAdmin) {
                throw new UnauthorizedException("You do not have permission to download this resume");
            }
        }

        try {
            Path filePath = Paths.get(resume.getStoragePath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("File not found on server");
            }
        } catch (MalformedURLException ex) {
            throw new ResourceNotFoundException("File not found on server");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResumeDto getResumeById(Long resumeId, Long currentUserId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume", "id", resumeId));
        return EntityMapper.toResumeDto(resume);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumeDto> getCandidateResumes(Long candidateId) {
        User candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", candidateId));

        return resumeRepository.findByCandidateOrderByUploadedAtDesc(candidate)
                .stream()
                .map(EntityMapper::toResumeDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteResume(Long resumeId, Long candidateId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume", "id", resumeId));

        if (!resume.getCandidate().getId().equals(candidateId)) {
            throw new UnauthorizedException("You do not have permission to delete this resume");
        }

        try {
            Path path = Paths.get(resume.getStoragePath());
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.warn("Could not delete file from disk: {}", e.getMessage());
        }

        CandidateProfile profile = candidateProfileRepository.findByUser(resume.getCandidate()).orElse(null);
        if (profile != null && profile.getActiveResume() != null && profile.getActiveResume().getId().equals(resumeId)) {
            profile.setActiveResume(null);
            candidateProfileRepository.save(profile);
        }

        resumeRepository.delete(resume);
    }
}

package com.jobtrack.service;

import com.jobtrack.dto.response.ResumeDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeStorageService {
    ResumeDto uploadResume(Long candidateId, MultipartFile file);
    Resource loadResumeAsResource(Long resumeId, Long currentUserId);
    ResumeDto getResumeById(Long resumeId, Long currentUserId);
    List<ResumeDto> getCandidateResumes(Long candidateId);
    void deleteResume(Long resumeId, Long candidateId);
}

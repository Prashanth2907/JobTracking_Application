package com.jobtrack.service.impl;

import com.jobtrack.dto.request.InterviewScheduleRequest;
import com.jobtrack.dto.response.InterviewDto;
import com.jobtrack.entity.*;
import com.jobtrack.exception.BadRequestException;
import com.jobtrack.exception.ResourceNotFoundException;
import com.jobtrack.exception.UnauthorizedException;
import com.jobtrack.mapper.EntityMapper;
import com.jobtrack.repository.*;
import com.jobtrack.service.InterviewService;
import com.jobtrack.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public InterviewServiceImpl(InterviewRepository interviewRepository, ApplicationRepository applicationRepository, UserRepository userRepository, NotificationService notificationService) {
        this.interviewRepository = interviewRepository;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public InterviewDto scheduleInterview(Long recruiterId, InterviewScheduleRequest request) {
        User recruiter = userRepository.findById(recruiterId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", recruiterId));

        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new ResourceNotFoundException("Application", "id", request.getApplicationId()));

        if (!application.getJob().getRecruiter().getId().equals(recruiterId) && recruiter.getRole() != Role.ROLE_ADMIN) {
            throw new UnauthorizedException("You are not authorized to schedule an interview for this candidate");
        }

        if (request.getInterviewDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Interview date cannot be in the past");
        }

        Interview interview = Interview.builder()
                .application(application)
                .recruiter(recruiter)
                .candidate(application.getCandidate())
                .interviewDate(request.getInterviewDate())
                .interviewTime(request.getInterviewTime())
                .interviewType(request.getInterviewType())
                .meetingLink(request.getMeetingLink())
                .locationAddress(request.getLocationAddress())
                .instructions(request.getInstructions())
                .status(InterviewStatus.SCHEDULED)
                .build();

        Interview saved = interviewRepository.save(interview);

        application.setStatus(ApplicationStatus.INTERVIEW_SCHEDULED);
        applicationRepository.save(application);

        String meetingDetails = request.getInterviewType() == InterviewType.ONLINE
                ? "via Google Meet / Zoom"
                : "at " + (request.getLocationAddress() != null ? request.getLocationAddress() : "Office");

        notificationService.sendNotification(
                application.getCandidate(),
                "Interview Scheduled: " + application.getJob().getTitle(),
                "Your interview has been scheduled for " + request.getInterviewDate() + " at " + request.getInterviewTime()
                        + " " + meetingDetails + ".",
                "INTERVIEW_SCHEDULED",
                "/candidate/interviews"
        );

        return EntityMapper.toInterviewDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterviewDto> getCandidateInterviews(Long candidateId) {
        User candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", candidateId));

        return interviewRepository.findByCandidateOrderByInterviewDateAscInterviewTimeAsc(candidate)
                .stream()
                .map(EntityMapper::toInterviewDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterviewDto> getRecruiterInterviews(Long recruiterId) {
        User recruiter = userRepository.findById(recruiterId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", recruiterId));

        return interviewRepository.findByRecruiterOrderByInterviewDateAscInterviewTimeAsc(recruiter)
                .stream()
                .map(EntityMapper::toInterviewDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InterviewDto updateInterview(Long interviewId, Long recruiterId, InterviewScheduleRequest request) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Interview", "id", interviewId));

        User user = userRepository.findById(recruiterId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", recruiterId));

        if (!interview.getRecruiter().getId().equals(recruiterId) && user.getRole() != Role.ROLE_ADMIN) {
            throw new UnauthorizedException("You are not authorized to update this interview");
        }

        if (request.getInterviewDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Interview date cannot be in the past");
        }

        interview.setInterviewDate(request.getInterviewDate());
        interview.setInterviewTime(request.getInterviewTime());
        interview.setInterviewType(request.getInterviewType());
        interview.setMeetingLink(request.getMeetingLink());
        interview.setLocationAddress(request.getLocationAddress());
        interview.setInstructions(request.getInstructions());
        interview.setStatus(InterviewStatus.RESCHEDULED);

        Interview updated = interviewRepository.save(interview);

        notificationService.sendNotification(
                interview.getCandidate(),
                "Interview Rescheduled: " + interview.getApplication().getJob().getTitle(),
                "Your interview has been rescheduled to " + request.getInterviewDate() + " at " + request.getInterviewTime() + ".",
                "INTERVIEW_RESCHEDULED",
                "/candidate/interviews"
        );

        return EntityMapper.toInterviewDto(updated);
    }

    @Override
    @Transactional
    public void cancelInterview(Long interviewId, Long recruiterId) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Interview", "id", interviewId));

        User user = userRepository.findById(recruiterId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", recruiterId));

        if (!interview.getRecruiter().getId().equals(recruiterId) && user.getRole() != Role.ROLE_ADMIN) {
            throw new UnauthorizedException("You are not authorized to cancel this interview");
        }

        interview.setStatus(InterviewStatus.CANCELLED);
        interviewRepository.save(interview);

        notificationService.sendNotification(
                interview.getCandidate(),
                "Interview Cancelled: " + interview.getApplication().getJob().getTitle(),
                "Your interview previously scheduled for " + interview.getInterviewDate() + " has been cancelled.",
                "INTERVIEW_CANCELLED",
                "/candidate/interviews"
        );
    }
}

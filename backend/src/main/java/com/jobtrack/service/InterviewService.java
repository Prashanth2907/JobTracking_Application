package com.jobtrack.service;

import com.jobtrack.dto.request.InterviewScheduleRequest;
import com.jobtrack.dto.response.InterviewDto;

import java.util.List;

public interface InterviewService {
    InterviewDto scheduleInterview(Long recruiterId, InterviewScheduleRequest request);
    List<InterviewDto> getCandidateInterviews(Long candidateId);
    List<InterviewDto> getRecruiterInterviews(Long recruiterId);
    InterviewDto updateInterview(Long interviewId, Long recruiterId, InterviewScheduleRequest request);
    void cancelInterview(Long interviewId, Long recruiterId);
}

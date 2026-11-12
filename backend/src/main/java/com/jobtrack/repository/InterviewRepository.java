package com.jobtrack.repository;

import com.jobtrack.entity.Interview;
import com.jobtrack.entity.InterviewStatus;
import com.jobtrack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    List<Interview> findByCandidateOrderByInterviewDateAscInterviewTimeAsc(User candidate);

    List<Interview> findByRecruiterOrderByInterviewDateAscInterviewTimeAsc(User recruiter);

    List<Interview> findByCandidateAndInterviewDateGreaterThanEqualOrderByInterviewDateAsc(User candidate, LocalDate date);

    List<Interview> findByRecruiterAndInterviewDateGreaterThanEqualOrderByInterviewDateAsc(User recruiter, LocalDate date);

    long countByCandidateAndStatus(User candidate, InterviewStatus status);

    long countByRecruiterAndStatus(User recruiter, InterviewStatus status);
}

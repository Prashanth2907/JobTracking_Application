package com.jobtrack.repository;

import com.jobtrack.entity.Application;
import com.jobtrack.entity.ApplicationStatus;
import com.jobtrack.entity.Job;
import com.jobtrack.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByJobIdAndCandidateId(Long jobId, Long candidateId);

    Optional<Application> findByJobIdAndCandidateId(Long jobId, Long candidateId);

    List<Application> findByCandidateOrderByAppliedAtDesc(User candidate);

    Page<Application> findByCandidateOrderByAppliedAtDesc(User candidate, Pageable pageable);

    List<Application> findByJobOrderByAppliedAtDesc(Job job);

    @Query("SELECT a FROM Application a WHERE a.job.recruiter = :recruiter ORDER BY a.appliedAt DESC")
    List<Application> findByRecruiter(@Param("recruiter") User recruiter);

    @Query("SELECT a FROM Application a WHERE a.job.recruiter = :recruiter ORDER BY a.appliedAt DESC")
    Page<Application> findByRecruiter(@Param("recruiter") User recruiter, Pageable pageable);

    long countByCandidate(User candidate);

    long countByCandidateAndStatus(User candidate, ApplicationStatus status);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.job.recruiter = :recruiter")
    long countByRecruiter(@Param("recruiter") User recruiter);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.job.recruiter = :recruiter AND a.status = :status")
    long countByRecruiterAndStatus(@Param("recruiter") User recruiter, @Param("status") ApplicationStatus status);

    long countByStatus(ApplicationStatus status);

    Page<Application> findAllByOrderByAppliedAtDesc(Pageable pageable);
}

package com.jobtrack.repository;

import com.jobtrack.entity.EmploymentType;
import com.jobtrack.entity.Job;
import com.jobtrack.entity.JobStatus;
import com.jobtrack.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByRecruiterOrderByPostedDateDesc(User recruiter);

    Page<Job> findByRecruiter(User recruiter, Pageable pageable);

    long countByStatus(JobStatus status);

    long countByRecruiter(User recruiter);

    long countByRecruiterAndStatus(User recruiter, JobStatus status);

    @Query("SELECT j FROM Job j WHERE j.status = :status " +
           "AND (:keyword IS NULL OR :keyword = '' OR LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "     OR LOWER(j.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "     OR LOWER(j.skillsRequired) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (:location IS NULL OR :location = '' OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) " +
           "AND (:employmentType IS NULL OR j.employmentType = :employmentType)")
    Page<Job> searchJobs(
            @Param("status") JobStatus status,
            @Param("keyword") String keyword,
            @Param("location") String location,
            @Param("employmentType") EmploymentType employmentType,
            Pageable pageable
    );

    Page<Job> findAllByOrderByPostedDateDesc(Pageable pageable);
}

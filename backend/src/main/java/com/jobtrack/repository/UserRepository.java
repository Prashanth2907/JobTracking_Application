package com.jobtrack.repository;

import com.jobtrack.entity.Role;
import com.jobtrack.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    long countByRole(Role role);
    Page<User> findAllByOrderByCreatedAtDesc(Pageable pageable);
}

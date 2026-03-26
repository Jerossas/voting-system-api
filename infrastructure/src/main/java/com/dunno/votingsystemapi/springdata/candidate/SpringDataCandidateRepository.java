package com.dunno.votingsystemapi.springdata.candidate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataCandidateRepository extends JpaRepository<CandidateEntity, Long> {
    boolean existsByEmail(String email);
    Optional<CandidateEntity> findByEmail(String email);
}

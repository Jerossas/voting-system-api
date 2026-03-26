package com.dunno.votingsystemapi.springdata.voter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataVoterRepository extends JpaRepository<VoterEntity, Long> {

    Optional<VoterEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}

package com.dunno.votingsystemapi.repositories;

import com.dunno.votingsystemapi.models.Candidate;
import com.dunno.votingsystemapi.models.Email;

import java.util.Optional;

public interface CandidateRepository {
    Candidate save(Candidate candidate);
    boolean existsByEmail(Email email);
    Optional<Candidate> findByEmail(Email email);
    boolean existsById(Long id);
    void deleteById(Long id);
}

package com.dunno.votingsystemapi.repositories;

import com.dunno.votingsystemapi.models.Candidate;
import com.dunno.votingsystemapi.models.Email;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository {
    Candidate save(Candidate candidate);
    boolean existsByEmail(Email email);
    Optional<Candidate> findByEmail(Email email);
    Optional<Candidate> findById(Long id);
    void deleteById(Long id);
    List<Candidate> findAll();
}

package com.dunno.votingsystemapi.repositories;

import com.dunno.votingsystemapi.models.Candidate;
import com.dunno.votingsystemapi.models.Email;

public interface CandidateRepository {
    Candidate save(Candidate candidate);
    boolean existsByEmail(Email email);
}

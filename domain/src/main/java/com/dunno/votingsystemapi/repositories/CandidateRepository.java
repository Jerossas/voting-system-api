package com.dunno.votingsystemapi.repositories;

import com.dunno.votingsystemapi.models.Email;

public interface CandidateRepository {
    boolean existsByEmail(Email email);
}

package com.dunno.votingsystemapi.repositories;

import com.dunno.votingsystemapi.models.Email;
import com.dunno.votingsystemapi.models.Voter;

import java.util.Optional;

public interface VoterRepository {
    Voter save(Voter voter);
    boolean existsByEmail(Email email);
    Optional<Voter> findByEmail(Email email);
}

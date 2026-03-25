package com.dunno.votingsystemapi.repositories;

import com.dunno.votingsystemapi.models.Email;
import com.dunno.votingsystemapi.models.Voter;

public interface VoterRepository {
    Voter save(Voter voter);
    boolean existsByEmail(Email email);
}

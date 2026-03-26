package com.dunno.votingsystemapi.repositories;

import com.dunno.votingsystemapi.models.Email;
import com.dunno.votingsystemapi.models.Voter;

import java.util.List;
import java.util.Optional;

public interface VoterRepository {
    Voter save(Voter voter);
    boolean existsByEmail(Email email);
    Optional<Voter> findByEmail(Email email);
    List<Voter> findAll();
    Optional<Voter> findById(Long id);
    void deleteById(Long id);
    long count();
}

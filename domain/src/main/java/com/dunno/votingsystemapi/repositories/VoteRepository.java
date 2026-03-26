package com.dunno.votingsystemapi.repositories;

import com.dunno.votingsystemapi.models.Vote;

import java.util.List;

public interface VoteRepository {

    List<Vote> findAll();
    boolean existsByVoterId(Long voterId);
}

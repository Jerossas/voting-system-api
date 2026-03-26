package com.dunno.votingsystemapi.repositories;

public interface VoteRepository {

    boolean existsByVoterId(Long voterId);
}

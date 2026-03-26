package com.dunno.votingsystemapi.springdata.vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataVoteRepository extends JpaRepository<VoteEntity, Long> {
    boolean existsByVoterId(Long voterId);
}

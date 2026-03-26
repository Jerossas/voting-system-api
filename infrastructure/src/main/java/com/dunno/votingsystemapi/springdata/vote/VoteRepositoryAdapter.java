package com.dunno.votingsystemapi.springdata.vote;

import com.dunno.votingsystemapi.repositories.VoteRepository;
import org.springframework.stereotype.Repository;

@Repository
public class VoteRepositoryAdapter implements VoteRepository {

    private final SpringDataVoteRepository springDataVoteRepository;

    public VoteRepositoryAdapter(SpringDataVoteRepository springDataVoteRepository) {
        this.springDataVoteRepository = springDataVoteRepository;
    }

    @Override
    public boolean existsByVoterId(Long voterId) {
        return springDataVoteRepository.existsByVoterId(voterId);
    }

}

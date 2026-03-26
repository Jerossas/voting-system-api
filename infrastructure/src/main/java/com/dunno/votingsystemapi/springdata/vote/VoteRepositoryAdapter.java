package com.dunno.votingsystemapi.springdata.vote;

import com.dunno.votingsystemapi.models.Vote;
import com.dunno.votingsystemapi.repositories.VoteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VoteRepositoryAdapter implements VoteRepository {

    private final SpringDataVoteRepository springDataVoteRepository;

    public VoteRepositoryAdapter(SpringDataVoteRepository springDataVoteRepository) {
        this.springDataVoteRepository = springDataVoteRepository;
    }

    @Override
    public List<Vote> findAll() {
        return springDataVoteRepository.findAll().stream()
                .map(entity -> Vote.restore(
                        entity.getId(),
                        entity.getVoterId(),
                        entity.getCandidateId()
                )).toList();
    }

    @Override
    public boolean existsByVoterId(Long voterId) {
        return springDataVoteRepository.existsByVoterId(voterId);
    }

}

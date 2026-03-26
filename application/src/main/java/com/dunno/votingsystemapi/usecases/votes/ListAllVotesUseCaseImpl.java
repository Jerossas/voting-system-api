package com.dunno.votingsystemapi.usecases.votes;

import com.dunno.votingsystemapi.commands.votes.ListAllVotesCommand;
import com.dunno.votingsystemapi.models.Vote;
import com.dunno.votingsystemapi.repositories.VoteRepository;

import java.util.List;

public class ListAllVotesUseCaseImpl implements ListAllVotesUseCase {

    private final VoteRepository voteRepository;

    public ListAllVotesUseCaseImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public List<Vote> execute(ListAllVotesCommand command) {
        return voteRepository.findAll();
    }
}

package com.dunno.votingsystemapi.usecases.voters;

import com.dunno.votingsystemapi.commands.voters.DeleteVoterByIdCommand;
import com.dunno.votingsystemapi.exceptions.VoterAlreadyVotedException;
import com.dunno.votingsystemapi.exceptions.VoterNotFoundException;
import com.dunno.votingsystemapi.models.Voter;
import com.dunno.votingsystemapi.repositories.VoteRepository;
import com.dunno.votingsystemapi.repositories.VoterRepository;

public class DeleteVoterByIdUseCaseImpl implements DeleteVoterByIdUseCase {

    private final VoterRepository voterRepository;
    private final VoteRepository voteRepository;

    public DeleteVoterByIdUseCaseImpl(VoterRepository voterRepository, VoteRepository voteRepository) {
        this.voterRepository = voterRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public Void execute(DeleteVoterByIdCommand command) {

        Voter voter = voterRepository.findById(command.voterId())
                .orElseThrow(() -> new VoterNotFoundException("Voter with id " + command.voterId() + " not found."));

        if (voteRepository.existsByVoterId(voter.getId())) {
            throw new VoterAlreadyVotedException("Voter with id " + command.voterId() + " has already voted and cannot be deleted.");
        }

        voterRepository.deleteById(voter.getId());

        return null;
    }
}

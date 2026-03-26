package com.dunno.votingsystemapi.usecases.voters;

import com.dunno.votingsystemapi.commands.voters.GetVoterByIdCommand;
import com.dunno.votingsystemapi.exceptions.VoterNotFoundException;
import com.dunno.votingsystemapi.models.Voter;
import com.dunno.votingsystemapi.repositories.VoterRepository;

public class GetVoterByIdUseCaseImpl implements GetVoterByIdUseCase {

    private final VoterRepository voterRepository;

    public GetVoterByIdUseCaseImpl(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    @Override
    public Voter execute(GetVoterByIdCommand command) {
        return voterRepository.findById(command.voterId())
                .orElseThrow(() -> new VoterNotFoundException("Voter with id " + command.voterId() + " not found."));
    }
}

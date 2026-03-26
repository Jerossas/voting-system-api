package com.dunno.votingsystemapi.usecases.voters;

import com.dunno.votingsystemapi.commands.voters.DeleteVoterByIdCommand;
import com.dunno.votingsystemapi.exceptions.VoterNotFoundException;
import com.dunno.votingsystemapi.repositories.VoterRepository;

public class DeleteVoterByIdUseCaseImpl implements DeleteVoterByIdUseCase {

    private final VoterRepository voterRepository;

    public DeleteVoterByIdUseCaseImpl(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    @Override
    public Void execute(DeleteVoterByIdCommand command) {

        if(!voterRepository.existsById(command.voterId())) {
            throw new VoterNotFoundException("Voter with id " + command.voterId() + " not found.");
        }

        voterRepository.deleteById(command.voterId());

        return null;
    }
}

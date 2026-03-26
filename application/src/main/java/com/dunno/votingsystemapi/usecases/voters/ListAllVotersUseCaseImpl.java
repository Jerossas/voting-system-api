package com.dunno.votingsystemapi.usecases.voters;

import com.dunno.votingsystemapi.commands.voters.ListAllVotersCommand;
import com.dunno.votingsystemapi.models.Voter;
import com.dunno.votingsystemapi.repositories.VoterRepository;

import java.util.List;

public class ListAllVotersUseCaseImpl implements ListAllVotersUseCase {

    private final VoterRepository voterRepository;

    public ListAllVotersUseCaseImpl(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    @Override
    public List<Voter> execute(ListAllVotersCommand command) {
        return voterRepository.findAll();
    }
}

package com.dunno.votingsystemapi.usecases.candidates;

import com.dunno.votingsystemapi.commands.candidates.DeleteCandidateByIdCommand;
import com.dunno.votingsystemapi.exceptions.CandidateNotFoundException;
import com.dunno.votingsystemapi.repositories.CandidateRepository;

public class DeleteCandidateByIdUseCaseImpl implements DeleteCandidateByIdUseCase {

    private final CandidateRepository candidateRepository;

    public DeleteCandidateByIdUseCaseImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Void execute(DeleteCandidateByIdCommand command) {

        if(!candidateRepository.existsById(command.candidateId())){
            throw new CandidateNotFoundException("Candidate with id " + command.candidateId() + " not found.");
        }

        candidateRepository.deleteById(command.candidateId());

        return null;
    }
}

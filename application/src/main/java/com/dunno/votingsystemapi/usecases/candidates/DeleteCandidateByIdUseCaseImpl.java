package com.dunno.votingsystemapi.usecases.candidates;

import com.dunno.votingsystemapi.commands.candidates.DeleteCandidateByIdCommand;
import com.dunno.votingsystemapi.exceptions.CandidateNotFoundException;
import com.dunno.votingsystemapi.models.Candidate;
import com.dunno.votingsystemapi.repositories.CandidateRepository;

public class DeleteCandidateByIdUseCaseImpl implements DeleteCandidateByIdUseCase {

    private final CandidateRepository candidateRepository;

    public DeleteCandidateByIdUseCaseImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Void execute(DeleteCandidateByIdCommand command) {

        Candidate candidate = candidateRepository.findById(command.candidateId())
                .orElseThrow(() -> new CandidateNotFoundException("Candidate with id " + command.candidateId() + " not found."));

        candidateRepository.deleteById(candidate.getId());

        return null;
    }
}

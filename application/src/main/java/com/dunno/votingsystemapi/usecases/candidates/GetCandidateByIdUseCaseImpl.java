package com.dunno.votingsystemapi.usecases.candidates;

import com.dunno.votingsystemapi.commands.candidates.GetCandidateByIdCommand;
import com.dunno.votingsystemapi.exceptions.CandidateNotFoundException;
import com.dunno.votingsystemapi.models.Candidate;
import com.dunno.votingsystemapi.repositories.CandidateRepository;

public class GetCandidateByIdUseCaseImpl implements GetCandidateByIdUseCase {

    private final CandidateRepository candidateRepository;

    public GetCandidateByIdUseCaseImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Candidate execute(GetCandidateByIdCommand command) {
        return candidateRepository.findById(command.candidateId())
                .orElseThrow(() -> new CandidateNotFoundException("Candidate with id " + command.candidateId() + " not found."));
    }
}

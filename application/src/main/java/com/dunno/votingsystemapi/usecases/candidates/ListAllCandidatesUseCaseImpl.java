package com.dunno.votingsystemapi.usecases.candidates;

import com.dunno.votingsystemapi.commands.candidates.ListAllCandidatesCommand;
import com.dunno.votingsystemapi.models.Candidate;
import com.dunno.votingsystemapi.repositories.CandidateRepository;

import java.util.List;

public class ListAllCandidatesUseCaseImpl implements ListAllCandidatesUseCase {

    private final CandidateRepository candidateRepository;

    public ListAllCandidatesUseCaseImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public List<Candidate> execute(ListAllCandidatesCommand command) {
        return candidateRepository.findAll();
    }
}

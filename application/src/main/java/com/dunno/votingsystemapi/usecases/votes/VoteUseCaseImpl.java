package com.dunno.votingsystemapi.usecases.votes;

import com.dunno.votingsystemapi.commands.votes.VoteCommand;
import com.dunno.votingsystemapi.exceptions.CandidateNotFoundException;
import com.dunno.votingsystemapi.exceptions.VoterAlreadyVotedException;
import com.dunno.votingsystemapi.exceptions.VoterNotFoundException;
import com.dunno.votingsystemapi.models.Candidate;
import com.dunno.votingsystemapi.models.Vote;
import com.dunno.votingsystemapi.models.Voter;
import com.dunno.votingsystemapi.repositories.CandidateRepository;
import com.dunno.votingsystemapi.repositories.VoteRepository;
import com.dunno.votingsystemapi.repositories.VoterRepository;

public class VoteUseCaseImpl implements VoteUseCase {

    private final VoteRepository voteRepository;
    private final CandidateRepository candidateRepository;
    private final VoterRepository voterRepository;

    public VoteUseCaseImpl(VoteRepository voteRepository, CandidateRepository candidateRepository, VoterRepository voterRepository) {
        this.voteRepository = voteRepository;
        this.candidateRepository = candidateRepository;
        this.voterRepository = voterRepository;
    }

    @Override
    public Vote execute(VoteCommand command) {

        Voter voter = voterRepository.findById(command.voterId())
                .orElseThrow(() -> new VoterNotFoundException("Voter with id " + command.voterId() + " has already voted and cannot be deleted."));

        if(voteRepository.existsByVoterId(voter.getId())) {
            throw new VoterAlreadyVotedException("Voter has already voted.");
        }

        Candidate candidate = candidateRepository.findById(command.candidateId())
                .orElseThrow(() -> new CandidateNotFoundException("Candidate with id " + command.candidateId() + " not found."));

        return voteRepository.save(new Vote(voter.getId(), candidate.getId()));
    }
}

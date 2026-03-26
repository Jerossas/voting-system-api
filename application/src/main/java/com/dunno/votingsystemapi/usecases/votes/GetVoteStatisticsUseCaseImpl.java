package com.dunno.votingsystemapi.usecases.votes;

import com.dunno.votingsystemapi.commands.votes.GetVoteStatisticsCommand;
import com.dunno.votingsystemapi.models.Candidate;
import com.dunno.votingsystemapi.models.CandidateResult;
import com.dunno.votingsystemapi.models.VoteStatistics;
import com.dunno.votingsystemapi.repositories.CandidateRepository;
import com.dunno.votingsystemapi.repositories.VoteRepository;
import com.dunno.votingsystemapi.repositories.VoterRepository;

import java.util.List;

public class GetVoteStatisticsUseCaseImpl implements GetVoteStatisticsUseCase {

    private final VoteRepository voteRepository;
    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;

    public GetVoteStatisticsUseCaseImpl(VoteRepository voteRepository, VoterRepository voterRepository, CandidateRepository candidateRepository) {
        this.voteRepository = voteRepository;
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
    }

    @Override
    public VoteStatistics execute(GetVoteStatisticsCommand command) {

        List<Candidate> candidates = candidateRepository.findAll();
        long totalVotesCast = voteRepository.count();
        long totalVotersRegistered = voterRepository.count();

        List<CandidateResult> results = candidates.stream()
                .map(candidate -> {
                    long votes = voteRepository.countByCandidateId(candidate.getId());
                    double percentage = totalVotesCast == 0 ? 0.0
                            : Math.round((votes * 100.0 / totalVotesCast) * 100.0) / 100.0;
                    return new CandidateResult(
                            candidate.getId(),
                            candidate.getFullName(),
                            candidate.getParty(),
                            votes,
                            percentage
                    );
                }).toList();

        return new VoteStatistics(totalVotesCast, totalVotersRegistered, results);
    }
}

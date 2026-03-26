package com.dunno.votingsystemapi.models;

import java.util.List;

public class VoteStatistics {

    private final long totalVotesCast;
    private final long totalVotersRegistered;
    private final List<CandidateResult> results;

    public VoteStatistics(long totalVotesCast, long totalVotersRegistered, List<CandidateResult> results) {
        this.totalVotesCast = totalVotesCast;
        this.totalVotersRegistered = totalVotersRegistered;
        this.results = results;
    }

    public long getTotalVotesCast() {
        return totalVotesCast;
    }

    public long getTotalVotersRegistered() {
        return totalVotersRegistered;
    }

    public List<CandidateResult> getResults() {
        return results;
    }
}

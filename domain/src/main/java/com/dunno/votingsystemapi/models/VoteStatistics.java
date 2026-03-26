package com.dunno.votingsystemapi.models;

import java.util.List;

public record VoteStatistics(
        long totalVotesCast,
        long totalVotersRegistered,
        List<CandidateResult> results
) {}

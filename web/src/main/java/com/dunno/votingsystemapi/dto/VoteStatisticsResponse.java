package com.dunno.votingsystemapi.dto;

import java.util.List;

public record VoteStatisticsResponse(
        long totalVotesCast,
        long totalVotersRegistered,
        List<CandidateResultResponse> results
) {}

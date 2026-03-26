package com.dunno.votingsystemapi.dto;

public record CandidateResultResponse(
        Long candidateId,
        String candidateName,
        String party,
        long votes,
        double percentage
) {}

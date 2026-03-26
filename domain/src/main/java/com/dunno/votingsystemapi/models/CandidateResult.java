package com.dunno.votingsystemapi.models;

public record CandidateResult(
        Long candidateId,
        String candidateName,
        String party,
        long votes,
        double percentage
) {}

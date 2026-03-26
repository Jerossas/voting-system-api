package com.dunno.votingsystemapi.dto;

public record VoteResponse(
        Long voteId,
        Long voterId,
        Long candidateId
) {}

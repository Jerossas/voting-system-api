package com.dunno.votingsystemapi.dto;

public record CandidatePublicResponse(
        Long id,
        String fullName,
        String party
) {}

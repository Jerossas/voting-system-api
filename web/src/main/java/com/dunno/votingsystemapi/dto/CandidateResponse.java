package com.dunno.votingsystemapi.dto;

public record CandidateResponse(
    String fullName,
    String party,
    String email,
    Long id
) {}

package com.dunno.votingsystemapi.dto;

public record CandidateResponse(
        Long id,
        String fullName,
        String party,
        String email
){}

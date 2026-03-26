package com.dunno.votingsystemapi.dto;

public record VoterResponse(
        Long id,
        String fullName,
        String email
) {}

package com.dunno.votingsystemapi.dto;

public record CreateCandidateRequest(
        String fullName,
        String party,
        String email,
        String password,
        String passwordConfirmation
) {}

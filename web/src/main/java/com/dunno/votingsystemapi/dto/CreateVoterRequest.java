package com.dunno.votingsystemapi.dto;

public record CreateVoterRequest(
        String fullName,
        String email,
        String password,
        String passwordConfirmation
) {}

package com.dunno.votingsystemapi.commands.auth;

public record RegisterCandidateCommand(
        String fullName,
        String email,
        String password,
        String passwordConfirmation,
        String party
) {}

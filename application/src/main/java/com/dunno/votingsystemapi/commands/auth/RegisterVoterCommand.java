package com.dunno.votingsystemapi.commands.auth;

public record RegisterVoterCommand(
        String fullName,
        String email,
        String password,
        String passwordConfirmation
) {}

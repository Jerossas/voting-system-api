package com.dunno.votingsystemapi.commands.auth;

public record SignInCommand(
        String email,
        String password
) {}

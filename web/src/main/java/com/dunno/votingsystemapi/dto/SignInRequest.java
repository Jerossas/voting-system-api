package com.dunno.votingsystemapi.dto;

public record SignInRequest(
        String email,
        String password
) {}

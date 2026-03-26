package com.dunno.votingsystemapi.commands.votes;

public record VoteCommand(
        Long candidateId,
        String email
) {}

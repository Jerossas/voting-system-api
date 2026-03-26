package com.dunno.votingsystemapi.commands.voters;

public record DeleteVoterByIdCommand(
        Long voterId
) {}

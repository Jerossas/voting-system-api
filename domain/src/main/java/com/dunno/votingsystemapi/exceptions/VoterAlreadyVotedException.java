package com.dunno.votingsystemapi.exceptions;

public class VoterAlreadyVotedException extends DomainException {

    private static final int HTTP_CODE = 409;

    public VoterAlreadyVotedException(String message) {
        super(message, HTTP_CODE);
    }
}

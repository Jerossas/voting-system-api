package com.dunno.votingsystemapi.exceptions;

public class VoterNotFoundException extends DomainException {

    private static final int HTTP_CODE = 404;

    public VoterNotFoundException(String message) {
        super(message, HTTP_CODE);
    }
}

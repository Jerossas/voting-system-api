package com.dunno.votingsystemapi.exceptions;

public class CandidateNotFoundException extends DomainException {

    private static final int HTTP_CODE = 404;

    public CandidateNotFoundException(String message) {
        super(message, HTTP_CODE);
    }
}

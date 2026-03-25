package com.dunno.votingsystemapi.models;

import com.dunno.votingsystemapi.exceptions.InvalidFieldException;

public class Candidate extends User {

    private String fullName;
    private String party;

    public Candidate(Email email, Password password, String fullName, String party) {
        super(email, password, Role.CANDIDATE);
        changeFullName(fullName);
        this.party = party;
    }

    private Candidate(Long id, Email email, Password password, String fullName, String party){
        super(id, email, password, Role.CANDIDATE);
        this.fullName = fullName;
        this.party = party;
    }

    public static Candidate restore(Long id, Email email, Password password, String fullName, String party){
        return new Candidate(id, email, password, fullName, party);
    }

    public void changeFullName(String fullName){

        if(fullName == null) {
            throw new InvalidFieldException("fullName", "Full name cannot be null");
        }

        if(fullName.isBlank()) {
            throw new InvalidFieldException("fullName", "Full name cannot be empty");
        }

        this.fullName = fullName;
    }


    public String getFullName() {
        return fullName;
    }

    public void changeParty(String party){
        this.party = party;
    }

    public String getParty() {
        return party;
    }
}

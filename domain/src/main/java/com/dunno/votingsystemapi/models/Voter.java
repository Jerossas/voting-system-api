package com.dunno.votingsystemapi.models;

import com.dunno.votingsystemapi.exceptions.InvalidFieldException;

public class Voter extends User {

    private String fullName;

    public Voter(Email email, Password password, String fullName) {
        super(email, password, Role.VOTER);

        changeFullName(fullName);
    }

    private Voter(Long id, String fullName, Email email, Password password) {
        super(id, email, password, Role.VOTER);
        this.fullName = fullName;
    }

    public static Voter restore(Long id, String fullName, Email email, Password password){
        return new Voter(id, fullName, email, password);
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
}

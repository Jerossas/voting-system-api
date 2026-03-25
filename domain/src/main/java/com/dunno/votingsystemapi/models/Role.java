package com.dunno.votingsystemapi.models;

import com.dunno.votingsystemapi.exceptions.InvalidFieldException;

public enum Role {
    ADMIN, VOTER, CANDIDATE;

    public static Role from(String value){
        if(value == null) {
            throw new InvalidFieldException("role", "Role cannot be null");
        }
        try {
            return Role.valueOf(value.toUpperCase());
        } catch(IllegalArgumentException e) {
            throw new InvalidFieldException("role", "Invalid role value: " + value);
        }
    }
}

package com.dunno.votingsystemapi.springdata.candidate;

import com.dunno.votingsystemapi.springdata.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "candidates")
public class CandidateEntity extends UserEntity {

    private String fullName;
    private String party;

    public CandidateEntity(Long id, String email, String password, String role, String fullName, String party) {
        super(id, email, password, role);
        this.fullName = fullName;
        this.party = party;
    }

    public CandidateEntity(){}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }
}

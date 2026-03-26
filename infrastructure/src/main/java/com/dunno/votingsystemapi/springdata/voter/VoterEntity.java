package com.dunno.votingsystemapi.springdata.voter;

import com.dunno.votingsystemapi.springdata.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "voters")
public class VoterEntity extends UserEntity {

    private String fullName;

    public VoterEntity(Long id, String email, String password, String role, String fullName) {
        super(id, email, password, role);
        this.fullName = fullName;
    }

    public VoterEntity(){}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

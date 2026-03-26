package com.dunno.votingsystemapi.springdata.admin;

import com.dunno.votingsystemapi.springdata.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
public class AdminEntity extends UserEntity {
    public AdminEntity(Long id, String email, String password, String role) {
        super(id, email, password, role);
    }

    public AdminEntity(){}
}

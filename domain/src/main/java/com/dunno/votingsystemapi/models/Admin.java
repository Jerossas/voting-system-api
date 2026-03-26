package com.dunno.votingsystemapi.models;

public class Admin extends User {

    public Admin(Email email, Password password) {
        super(email, password, Role.ADMIN);
    }

    private Admin(Long id, Email email, Password password) {
        super(id, email, password, Role.ADMIN);
    }

    public static Admin restore(Long id, Email email, Password password) {
        return new Admin(id, email, password);
    }
}
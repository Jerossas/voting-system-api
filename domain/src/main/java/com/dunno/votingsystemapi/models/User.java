package com.dunno.votingsystemapi.models;

import com.dunno.votingsystemapi.exceptions.InvalidFieldException;

public abstract class User {

    private Long id;
    private Email email;
    private Password password;
    private final Role role;

    public User(Email email, Password password, Role role){
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void changePassword(Password newPassword){

        if(newPassword == null) {
            throw new InvalidFieldException("password", "The password cannot be null");
        }

        this.password = newPassword;
    }

    public Password getPassword() {
        return password;
    }

    public void changeEmail(Email email){

        if(email == null) {
            throw new InvalidFieldException("email", "email cannot be null");
        }

        this.email = email;
    }

    public Email getEmail(){
        return this.email;
    }

    public Role getRole() {
        return role;
    }
}

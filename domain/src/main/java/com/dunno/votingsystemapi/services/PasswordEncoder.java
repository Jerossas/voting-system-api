package com.dunno.votingsystemapi.services;

public interface PasswordEncoder {

    String encode(String rawString);
    boolean matches(String rawPassword, String encodedPassword);
}

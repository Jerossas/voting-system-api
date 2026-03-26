package com.dunno.votingsystemapi.services;

import com.dunno.votingsystemapi.models.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generate(User user);
    String extractEmail(String token);
    boolean isValid(String token, UserDetails userDetails);
}

package com.dunno.votingsystemapi.repositories;

import com.dunno.votingsystemapi.models.Admin;
import com.dunno.votingsystemapi.models.Email;

import java.util.Optional;

public interface AdminRepository {
    Optional<Admin> findByEmail(Email email);
}

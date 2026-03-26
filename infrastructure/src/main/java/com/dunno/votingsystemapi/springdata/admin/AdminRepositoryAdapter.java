package com.dunno.votingsystemapi.springdata.admin;

import com.dunno.votingsystemapi.models.Admin;
import com.dunno.votingsystemapi.models.Email;
import com.dunno.votingsystemapi.models.Password;
import com.dunno.votingsystemapi.repositories.AdminRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AdminRepositoryAdapter implements AdminRepository {

    private final SpringDataAdminRepository springDataAdminRepository;

    public AdminRepositoryAdapter(SpringDataAdminRepository springDataAdminRepository) {
        this.springDataAdminRepository = springDataAdminRepository;
    }

    @Override
    public Optional<Admin> findByEmail(Email email) {

        return springDataAdminRepository.findByEmail(email.getValue())
                .map(adminEntity -> Admin.restore(
                        adminEntity.getId(),
                        Email.fromStored(adminEntity.getEmail()),
                        Password.fromEncoded(adminEntity.getPassword())
                ));
    }
}

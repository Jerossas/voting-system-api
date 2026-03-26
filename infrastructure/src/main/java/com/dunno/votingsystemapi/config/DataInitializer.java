package com.dunno.votingsystemapi.config;

import com.dunno.votingsystemapi.springdata.admin.AdminEntity;
import com.dunno.votingsystemapi.springdata.admin.SpringDataAdminRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    private final SpringDataAdminRepository adminRepository;
    private final BCryptPasswordEncoder encoder;

    public DataInitializer(SpringDataAdminRepository adminRepository, BCryptPasswordEncoder encoder) {
        this.adminRepository = adminRepository;
        this.encoder = encoder;
    }

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(ApplicationArguments args) {
        if (adminRepository.findByEmail(adminEmail).isEmpty()) {
            adminRepository.save(new AdminEntity(
                    null,
                    adminEmail,
                    encoder.encode(adminPassword),
                    "ADMIN"
            ));
        }
    }
}
package com.dunno.votingsystemapi.usecases.auth;

import com.dunno.votingsystemapi.commands.auth.SignInCommand;
import com.dunno.votingsystemapi.exceptions.InvalidCredentialsException;
import com.dunno.votingsystemapi.models.Email;
import com.dunno.votingsystemapi.models.User;
import com.dunno.votingsystemapi.repositories.AdminRepository;
import com.dunno.votingsystemapi.repositories.CandidateRepository;
import com.dunno.votingsystemapi.repositories.VoterRepository;
import com.dunno.votingsystemapi.services.PasswordEncoder;

public class SignInUseCaseImpl implements SignInUseCase {

    private final PasswordEncoder passwordEncoder;
    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;
    private final AdminRepository adminRepository;

    public SignInUseCaseImpl(PasswordEncoder passwordEncoder, VoterRepository voterRepository, CandidateRepository candidateRepository, AdminRepository adminRepository) {
        this.passwordEncoder = passwordEncoder;
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public User execute(SignInCommand command) {

        Email email = Email.of(command.email());

        User user = voterRepository.findByEmail(email)
                .map(v -> (User) v)
                .or(() -> candidateRepository.findByEmail(email).map(c -> (User) c))
                .or(() -> adminRepository.findByEmail(email).map(a -> (User) a))
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password."));

        if (!passwordEncoder.matches(command.password(), user.getPassword().getValue())) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }

        return user;
    }
}

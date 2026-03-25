package com.dunno.votingsystemapi.usecases.auth;

import com.dunno.votingsystemapi.commands.auth.RegisterCandidateCommand;
import com.dunno.votingsystemapi.exceptions.EmailAlreadyRegisteredException;
import com.dunno.votingsystemapi.exceptions.InvalidFieldException;
import com.dunno.votingsystemapi.models.Candidate;
import com.dunno.votingsystemapi.models.Email;
import com.dunno.votingsystemapi.models.Password;
import com.dunno.votingsystemapi.repositories.CandidateRepository;
import com.dunno.votingsystemapi.repositories.VoterRepository;
import com.dunno.votingsystemapi.services.PasswordEncoder;

public class RegisterCandidateUseCaseImpl implements RegisterCandidateUseCase {

    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterCandidateUseCaseImpl(VoterRepository voterRepository, CandidateRepository candidateRepository, PasswordEncoder passwordEncoder) {
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Candidate execute(RegisterCandidateCommand command) {

        if (command.password() == null || command.passwordConfirmation() == null) {
            throw new InvalidFieldException("password", "Password and confirmation cannot be null.");
        }

        if (!command.password().equals(command.passwordConfirmation())) {
            throw new InvalidFieldException("passwordConfirmation", "Passwords do not match.");
        }

        Password.validate(command.password());

        Email candidateEmail = Email.of(command.email());

        if(candidateRepository.existsByEmail(candidateEmail)) {
            throw new EmailAlreadyRegisteredException("Email " + candidateEmail.getValue() + " is already registered. Try with a different one.");
        }

        if(voterRepository.existsByEmail(candidateEmail)) {
            throw new EmailAlreadyRegisteredException("Email already registered as a voter.");
        }

        Candidate candidate = new Candidate(
                candidateEmail,
                Password.fromEncoded(passwordEncoder.encode(command.password())),
                command.fullName(),
                command.party()
        );

        return candidateRepository.save(candidate);
    }
}

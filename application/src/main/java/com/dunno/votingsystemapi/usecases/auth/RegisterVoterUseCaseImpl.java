package com.dunno.votingsystemapi.usecases.auth;

import com.dunno.votingsystemapi.commands.auth.RegisterVoterCommand;
import com.dunno.votingsystemapi.exceptions.EmailAlreadyRegisteredException;
import com.dunno.votingsystemapi.exceptions.InvalidFieldException;
import com.dunno.votingsystemapi.models.Email;
import com.dunno.votingsystemapi.models.Password;
import com.dunno.votingsystemapi.models.Voter;
import com.dunno.votingsystemapi.repositories.CandidateRepository;
import com.dunno.votingsystemapi.repositories.VoterRepository;
import com.dunno.votingsystemapi.services.PasswordEncoder;

public class RegisterVoterUseCaseImpl implements RegisterVoterUseCase {

    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterVoterUseCaseImpl(VoterRepository voterRepository, CandidateRepository candidateRepository, PasswordEncoder passwordEncoder) {
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Voter execute(RegisterVoterCommand command) {

        if (command.password() == null || command.passwordConfirmation() == null) {
            throw new InvalidFieldException("password", "Password and confirmation cannot be null.");
        }

        if (!command.password().equals(command.passwordConfirmation())) {
            throw new InvalidFieldException("passwordConfirmation", "Passwords do not match.");
        }

        Password.validate(command.password());

        Email voterEmail = Email.of(command.email());

        if(voterRepository.existsByEmail(voterEmail)) {
            throw new EmailAlreadyRegisteredException("Email " + voterEmail.getValue() + " is already registered. Try with a different one.");
        }

        if(candidateRepository.existsByEmail(voterEmail)) {
            throw new EmailAlreadyRegisteredException("Email already registered as a candidate.");
        }

        Voter voter = new Voter(
                voterEmail,
                Password.fromEncoded(passwordEncoder.encode(command.password())),
                command.fullName()
        );

        return voterRepository.save(voter);
    }
}

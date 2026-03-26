package com.dunno.votingsystemapi.config;

import com.dunno.votingsystemapi.repositories.AdminRepository;
import com.dunno.votingsystemapi.repositories.CandidateRepository;
import com.dunno.votingsystemapi.repositories.VoteRepository;
import com.dunno.votingsystemapi.repositories.VoterRepository;
import com.dunno.votingsystemapi.services.PasswordEncoder;
import com.dunno.votingsystemapi.usecases.auth.*;
import com.dunno.votingsystemapi.usecases.candidates.*;
import com.dunno.votingsystemapi.usecases.voters.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public RegisterVoterUseCase registerVoterUseCase(
            VoterRepository voterRepository,
            CandidateRepository candidateRepository,
            PasswordEncoder  passwordEncoder
    ){
        return new RegisterVoterUseCaseImpl(voterRepository, candidateRepository, passwordEncoder);
    }

    @Bean
    public RegisterCandidateUseCase registerCandidateUseCase(
            VoterRepository voterRepository,
            CandidateRepository candidateRepository,
            PasswordEncoder  passwordEncoder
    ){
        return new RegisterCandidateUseCaseImpl(voterRepository, candidateRepository, passwordEncoder);
    }

    @Bean
    public SignInUseCase signInUseCase(
            VoterRepository voterRepository,
            CandidateRepository candidateRepository,
            PasswordEncoder  passwordEncoder,
            AdminRepository adminRepository
    ){
        return new SignInUseCaseImpl(passwordEncoder, voterRepository, candidateRepository, adminRepository);
    }

    @Bean
    public ListAllVotersUseCase listAllVotersUseCase(VoterRepository voterRepository){
        return new ListAllVotersUseCaseImpl(voterRepository);
    }

    @Bean
    public GetVoterByIdUseCase getVoterByIdUseCase(VoterRepository voterRepository){
        return new GetVoterByIdUseCaseImpl(voterRepository);
    }

    @Bean
    public DeleteVoterByIdUseCase deleteVoterByIdUseCase(VoterRepository voterRepository, VoteRepository voteRepository){
        return new DeleteVoterByIdUseCaseImpl(voterRepository, voteRepository);
    }

    @Bean
    public DeleteCandidateByIdUseCase deleteCandidateByIdUseCase(CandidateRepository candidateRepository){
        return new DeleteCandidateByIdUseCaseImpl(candidateRepository);
    }

    @Bean
    public ListAllCandidatesUseCase listAllCandidatesUseCase(CandidateRepository candidateRepository){
        return new ListAllCandidatesUseCaseImpl(candidateRepository);
    }

    @Bean
    public GetCandidateByIdUseCase getCandidateByIdUseCase(CandidateRepository candidateRepository) {
        return new GetCandidateByIdUseCaseImpl(candidateRepository);
    }

}

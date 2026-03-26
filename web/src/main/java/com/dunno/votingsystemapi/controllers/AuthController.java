package com.dunno.votingsystemapi.controllers;

import com.dunno.votingsystemapi.commands.auth.RegisterCandidateCommand;
import com.dunno.votingsystemapi.commands.auth.RegisterVoterCommand;
import com.dunno.votingsystemapi.commands.auth.SignInCommand;
import com.dunno.votingsystemapi.dto.*;
import com.dunno.votingsystemapi.models.Candidate;
import com.dunno.votingsystemapi.models.User;
import com.dunno.votingsystemapi.models.Voter;
import com.dunno.votingsystemapi.services.JwtService;
import com.dunno.votingsystemapi.usecases.auth.RegisterCandidateUseCase;
import com.dunno.votingsystemapi.usecases.auth.RegisterVoterUseCase;
import com.dunno.votingsystemapi.usecases.auth.SignInUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final RegisterCandidateUseCase registerCandidateUseCase;
    private final RegisterVoterUseCase registerVoterUseCase;
    private final SignInUseCase signInUseCase;
    private final JwtService jwtService;

    public AuthController(
            RegisterCandidateUseCase registerCandidateUseCase,
            RegisterVoterUseCase registerVoterUseCase,
            SignInUseCase signInUseCase,
            JwtService jwtService
    ){
        this.registerCandidateUseCase = registerCandidateUseCase;
        this.registerVoterUseCase = registerVoterUseCase;
        this.signInUseCase = signInUseCase;
        this.jwtService = jwtService;
    }

    @PostMapping("/candidates")
    public ResponseEntity<CandidateResponse> registerCandidate(@RequestBody CreateCandidateRequest request){

        RegisterCandidateCommand command = new RegisterCandidateCommand(
                request.fullName(),
                request.email(),
                request.password(),
                request.passwordConfirmation(),
                request.party()
        );

        Candidate candidate = registerCandidateUseCase.execute(command);

        CandidateResponse response = new CandidateResponse(
                candidate.getFullName(),
                candidate.getParty(),
                candidate.getEmail().getValue(),
                candidate.getId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/voters")
    public ResponseEntity<VoterResponse> registerVoter(@RequestBody CreateVoterRequest request){

        RegisterVoterCommand command = new RegisterVoterCommand(
                request.fullName(),
                request.email(),
                request.password(),
                request.passwordConfirmation()
        );

        Voter voter = registerVoterUseCase.execute(command);

        VoterResponse response = new VoterResponse(
                voter.getId(),
                voter.getFullName(),
                voter.getEmail().getValue()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/auth/sign-in")
    public ResponseEntity<TokenResponse> signIn(@RequestBody SignInRequest request){

        SignInCommand command = new SignInCommand(
                request.email(),
                request.password()
        );

        User user = signInUseCase.execute(command);

        TokenResponse response = new TokenResponse(
                jwtService.generate(user)
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

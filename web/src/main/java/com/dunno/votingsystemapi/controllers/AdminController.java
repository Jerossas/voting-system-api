package com.dunno.votingsystemapi.controllers;

import com.dunno.votingsystemapi.commands.auth.RegisterCandidateCommand;
import com.dunno.votingsystemapi.commands.auth.RegisterVoterCommand;
import com.dunno.votingsystemapi.commands.candidates.DeleteCandidateByIdCommand;
import com.dunno.votingsystemapi.commands.candidates.GetCandidateByIdCommand;
import com.dunno.votingsystemapi.commands.candidates.ListAllCandidatesCommand;
import com.dunno.votingsystemapi.commands.voters.DeleteVoterByIdCommand;
import com.dunno.votingsystemapi.commands.voters.GetVoterByIdCommand;
import com.dunno.votingsystemapi.commands.voters.ListAllVotersCommand;
import com.dunno.votingsystemapi.commands.votes.ListAllVotesCommand;
import com.dunno.votingsystemapi.dto.*;
import com.dunno.votingsystemapi.models.Candidate;
import com.dunno.votingsystemapi.models.Voter;
import com.dunno.votingsystemapi.usecases.auth.RegisterCandidateUseCase;
import com.dunno.votingsystemapi.usecases.auth.RegisterVoterUseCase;
import com.dunno.votingsystemapi.usecases.candidates.DeleteCandidateByIdUseCase;
import com.dunno.votingsystemapi.usecases.candidates.GetCandidateByIdUseCase;
import com.dunno.votingsystemapi.usecases.candidates.ListAllCandidatesUseCase;
import com.dunno.votingsystemapi.usecases.voters.DeleteVoterByIdUseCase;
import com.dunno.votingsystemapi.usecases.voters.GetVoterByIdUseCase;
import com.dunno.votingsystemapi.usecases.voters.ListAllVotersUseCase;
import com.dunno.votingsystemapi.usecases.votes.ListAllVotesUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final RegisterVoterUseCase registerVoterUseCase;
    private final ListAllVotersUseCase listAllVotersUseCase;
    private final GetVoterByIdUseCase getVoterByIdUseCase;
    private final DeleteVoterByIdUseCase deleteVoterByIdUseCase;
    private final RegisterCandidateUseCase registerCandidateUseCase;
    private final ListAllCandidatesUseCase listAllCandidatesUseCase;
    private final GetCandidateByIdUseCase getCandidateByIdUseCase;
    private final DeleteCandidateByIdUseCase deleteCandidateByIdUseCase;
    private final ListAllVotesUseCase listAllVotesUseCase;

    public AdminController(
            RegisterVoterUseCase registerVoterUseCase,
            ListAllVotersUseCase listAllVotersUseCase,
            GetVoterByIdUseCase getVoterByIdUseCase,
            DeleteVoterByIdUseCase deleteVoterByIdUseCase,
            RegisterCandidateUseCase registerCandidateUseCase,
            ListAllCandidatesUseCase listAllCandidatesUseCase,
            GetCandidateByIdUseCase getCandidateByIdUseCase,
            DeleteCandidateByIdUseCase deleteCandidateByIdUseCase,
            ListAllVotesUseCase listAllVotesUseCase
    ) {
        this.registerVoterUseCase = registerVoterUseCase;
        this.listAllVotersUseCase = listAllVotersUseCase;
        this.getVoterByIdUseCase = getVoterByIdUseCase;
        this.deleteVoterByIdUseCase = deleteVoterByIdUseCase;
        this.registerCandidateUseCase = registerCandidateUseCase;
        this.listAllCandidatesUseCase = listAllCandidatesUseCase;
        this.getCandidateByIdUseCase = getCandidateByIdUseCase;
        this.deleteCandidateByIdUseCase = deleteCandidateByIdUseCase;
        this.listAllVotesUseCase = listAllVotesUseCase;
    }

    // VOTERS

    @PostMapping("/voters")
    public ResponseEntity<VoterResponse> registerVoter(@RequestBody CreateVoterRequest request) {
        RegisterVoterCommand command = new RegisterVoterCommand(
                request.fullName(), request.email(), request.password(), request.passwordConfirmation()
        );
        Voter voter = registerVoterUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new VoterResponse(voter.getId(), voter.getFullName(), voter.getEmail().getValue())
        );
    }

    @GetMapping("/voters")
    public ResponseEntity<List<VoterResponse>> listAllVoters() {
        return ResponseEntity.ok(
                listAllVotersUseCase.execute(new ListAllVotersCommand()).stream()
                        .map(voter -> new VoterResponse(voter.getId(), voter.getFullName(), voter.getEmail().getValue()))
                        .toList()
        );
    }

    @GetMapping("/voters/{voterId}")
    public ResponseEntity<VoterResponse> getVoterById(@PathVariable Long voterId) {
        Voter voter = getVoterByIdUseCase.execute(new GetVoterByIdCommand(voterId));
        return ResponseEntity.ok(
                new VoterResponse(voter.getId(), voter.getFullName(), voter.getEmail().getValue())
        );
    }

    @DeleteMapping("/voters/{voterId}")
    public ResponseEntity<Void> deleteVoterById(@PathVariable Long voterId) {
        deleteVoterByIdUseCase.execute(new DeleteVoterByIdCommand(voterId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // CANDIDATES

    @PostMapping("/candidates")
    public ResponseEntity<CandidateResponse> registerCandidate(@RequestBody CreateCandidateRequest request) {
        RegisterCandidateCommand command = new RegisterCandidateCommand(
                request.fullName(), request.email(), request.password(), request.passwordConfirmation(), request.party()
        );
        Candidate candidate = registerCandidateUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CandidateResponse(candidate.getId(), candidate.getFullName(), candidate.getParty(), candidate.getEmail().getValue())
        );
    }

    @GetMapping("/candidates")
    public ResponseEntity<List<CandidateResponse>> listAllCandidates() {
        return ResponseEntity.ok(
                listAllCandidatesUseCase.execute(new ListAllCandidatesCommand()).stream()
                        .map(candidate -> new CandidateResponse(candidate.getId(), candidate.getFullName(), candidate.getParty(), candidate.getEmail().getValue()))
                        .toList()
        );
    }

    @GetMapping("/candidates/{candidateId}")
    public ResponseEntity<CandidateResponse> getCandidateById(@PathVariable Long candidateId) {
        Candidate candidate = getCandidateByIdUseCase.execute(new GetCandidateByIdCommand(candidateId));
        return ResponseEntity.ok(
                new CandidateResponse(candidate.getId(), candidate.getFullName(), candidate.getParty(), candidate.getEmail().getValue())
        );
    }

    @DeleteMapping("/candidates/{candidateId}")
    public ResponseEntity<Void> deleteCandidateById(@PathVariable Long candidateId) {
        deleteCandidateByIdUseCase.execute(new DeleteCandidateByIdCommand(candidateId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // VOTES

    @GetMapping("/votes")
    public ResponseEntity<List<VoteResponse>> listAllVotes() {
        return ResponseEntity.ok(
                listAllVotesUseCase.execute(new ListAllVotesCommand()).stream()
                        .map(vote -> new VoteResponse(vote.getId(), vote.getVoterId(), vote.getCandidateId()))
                        .toList()
        );
    }
}
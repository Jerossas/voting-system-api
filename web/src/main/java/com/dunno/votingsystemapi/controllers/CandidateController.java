package com.dunno.votingsystemapi.controllers;

import com.dunno.votingsystemapi.commands.candidates.GetCandidateByIdCommand;
import com.dunno.votingsystemapi.commands.candidates.ListAllCandidatesCommand;
import com.dunno.votingsystemapi.dto.CandidatePublicResponse;
import com.dunno.votingsystemapi.models.Candidate;
import com.dunno.votingsystemapi.usecases.candidates.GetCandidateByIdUseCase;
import com.dunno.votingsystemapi.usecases.candidates.ListAllCandidatesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private final ListAllCandidatesUseCase listAllCandidatesUseCase;
    private final GetCandidateByIdUseCase getCandidateByIdUseCase;

    public CandidateController(
            ListAllCandidatesUseCase listAllCandidatesUseCase,
            GetCandidateByIdUseCase getCandidateByIdUseCase
    ) {
        this.listAllCandidatesUseCase = listAllCandidatesUseCase;
        this.getCandidateByIdUseCase = getCandidateByIdUseCase;
    }

    @GetMapping
    public ResponseEntity<List<CandidatePublicResponse>> listAllCandidates() {
        return ResponseEntity.ok(
                listAllCandidatesUseCase.execute(new ListAllCandidatesCommand()).stream()
                        .map(c -> new CandidatePublicResponse(c.getId(), c.getFullName(), c.getParty()))
                        .toList()
        );
    }

    @GetMapping("/{candidateId}")
    public ResponseEntity<CandidatePublicResponse> getCandidateById(@PathVariable Long candidateId) {
        Candidate candidate = getCandidateByIdUseCase.execute(new GetCandidateByIdCommand(candidateId));
        return ResponseEntity.ok(new CandidatePublicResponse(candidate.getId(), candidate.getFullName(), candidate.getParty()));
    }
}

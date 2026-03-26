package com.dunno.votingsystemapi.controllers;

import com.dunno.votingsystemapi.commands.candidates.DeleteCandidateByIdCommand;
import com.dunno.votingsystemapi.commands.candidates.ListAllCandidatesCommand;
import com.dunno.votingsystemapi.dto.CandidateResponse;
import com.dunno.votingsystemapi.usecases.candidates.DeleteCandidateByIdUseCase;
import com.dunno.votingsystemapi.usecases.candidates.ListAllCandidatesUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private final DeleteCandidateByIdUseCase deleteCandidateByIdUseCase;
    private final ListAllCandidatesUseCase listAllCandidatesUseCase;

    public CandidateController(DeleteCandidateByIdUseCase deleteCandidateByIdUseCase, ListAllCandidatesUseCase listAllCandidatesUseCase) {
        this.deleteCandidateByIdUseCase = deleteCandidateByIdUseCase;
        this.listAllCandidatesUseCase = listAllCandidatesUseCase;
    }

    @DeleteMapping("/{candidateId}")
    public ResponseEntity<Void> deleteCandidateById(@PathVariable("candidateId") Long candidateId) {

        DeleteCandidateByIdCommand command = new DeleteCandidateByIdCommand(candidateId);

        deleteCandidateByIdUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping
    public ResponseEntity<List<CandidateResponse>> listAllCandidates() {
        return ResponseEntity.ok(
                listAllCandidatesUseCase.execute(new ListAllCandidatesCommand()).stream()
                        .map(c -> new CandidateResponse(
                                c.getFullName(),
                                c.getParty(),
                                c.getEmail().getValue(),
                                c.getId()
                        )).toList()
        );
    }
}

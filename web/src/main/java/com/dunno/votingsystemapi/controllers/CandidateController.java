package com.dunno.votingsystemapi.controllers;

import com.dunno.votingsystemapi.commands.candidates.DeleteCandidateByIdCommand;
import com.dunno.votingsystemapi.usecases.candidates.DeleteCandidateByIdUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private final DeleteCandidateByIdUseCase deleteCandidateByIdUseCase;

    public CandidateController(DeleteCandidateByIdUseCase deleteCandidateByIdUseCase) {
        this.deleteCandidateByIdUseCase = deleteCandidateByIdUseCase;
    }

    @DeleteMapping("/{candidateId}")
    public ResponseEntity<Void> deleteCandidateById(@PathVariable("candidateId") Long candidateId) {

        DeleteCandidateByIdCommand command = new DeleteCandidateByIdCommand(candidateId);

        deleteCandidateByIdUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}

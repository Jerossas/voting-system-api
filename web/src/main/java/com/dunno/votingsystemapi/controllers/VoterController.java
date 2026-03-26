package com.dunno.votingsystemapi.controllers;

import com.dunno.votingsystemapi.commands.voters.ListAllVotersCommand;
import com.dunno.votingsystemapi.dto.VoterResponse;
import com.dunno.votingsystemapi.usecases.voters.ListAllVotersUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/voters")
public class VoterController {

    private final ListAllVotersUseCase listAllVotersUseCase;

    public VoterController(ListAllVotersUseCase listAllVotersUseCase) {
        this.listAllVotersUseCase = listAllVotersUseCase;
    }

    @GetMapping
    public ResponseEntity<List<VoterResponse>> listAllVoters(){

        return ResponseEntity.status(HttpStatus.OK).body(
                listAllVotersUseCase.execute(new ListAllVotersCommand()).stream()
                        .map(voter -> new VoterResponse(
                                voter.getFullName(),
                                voter.getEmail().getValue()
                        ))
                        .toList()
        );
    }
}

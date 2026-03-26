package com.dunno.votingsystemapi.controllers;

import com.dunno.votingsystemapi.commands.voters.GetVoterByIdCommand;
import com.dunno.votingsystemapi.commands.voters.ListAllVotersCommand;
import com.dunno.votingsystemapi.dto.VoterResponse;
import com.dunno.votingsystemapi.models.Voter;
import com.dunno.votingsystemapi.usecases.voters.GetVoterByIdUseCase;
import com.dunno.votingsystemapi.usecases.voters.ListAllVotersUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/voters")
public class VoterController {

    private final ListAllVotersUseCase listAllVotersUseCase;
    private final GetVoterByIdUseCase getVoterByIdUseCase;

    public VoterController(ListAllVotersUseCase listAllVotersUseCase, GetVoterByIdUseCase getVoterByIdUseCase) {
        this.listAllVotersUseCase = listAllVotersUseCase;
        this.getVoterByIdUseCase = getVoterByIdUseCase;
    }

    @GetMapping
    public ResponseEntity<List<VoterResponse>> listAllVoters(){

        return ResponseEntity.status(HttpStatus.OK).body(
                listAllVotersUseCase.execute(new ListAllVotersCommand()).stream()
                        .map(voter -> new VoterResponse(
                                voter.getId(),
                                voter.getFullName(),
                                voter.getEmail().getValue()
                        ))
                        .toList()
        );
    }

    @GetMapping("/{voterId}")
    public ResponseEntity<VoterResponse> getVoterById(@PathVariable("voterId") Long voterId) {

        GetVoterByIdCommand command = new GetVoterByIdCommand(voterId);

        Voter voter = getVoterByIdUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.OK).body(new VoterResponse(
                voter.getId(),
                voter.getFullName(),
                voter.getEmail().getValue()
        ));
    }
}

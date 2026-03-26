package com.dunno.votingsystemapi.controllers;

import com.dunno.votingsystemapi.commands.voters.DeleteVoterByIdCommand;
import com.dunno.votingsystemapi.commands.voters.GetVoterByIdCommand;
import com.dunno.votingsystemapi.commands.voters.ListAllVotersCommand;
import com.dunno.votingsystemapi.dto.VoterResponse;
import com.dunno.votingsystemapi.models.Voter;
import com.dunno.votingsystemapi.usecases.voters.DeleteVoterByIdUseCase;
import com.dunno.votingsystemapi.usecases.voters.GetVoterByIdUseCase;
import com.dunno.votingsystemapi.usecases.voters.ListAllVotersUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voters")
public class VoterController {

    private final ListAllVotersUseCase listAllVotersUseCase;
    private final GetVoterByIdUseCase getVoterByIdUseCase;
    private final DeleteVoterByIdUseCase deleteVoterByIdUseCase;

    public VoterController(ListAllVotersUseCase listAllVotersUseCase, GetVoterByIdUseCase getVoterByIdUseCase, DeleteVoterByIdUseCase deleteVoterByIdUseCase) {
        this.listAllVotersUseCase = listAllVotersUseCase;
        this.getVoterByIdUseCase = getVoterByIdUseCase;
        this.deleteVoterByIdUseCase = deleteVoterByIdUseCase;
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

    @DeleteMapping("/{voterId}")
    public ResponseEntity<Void> deleteVoterById(@PathVariable("voterId") Long voterId){

        DeleteVoterByIdCommand command = new DeleteVoterByIdCommand(voterId);

        deleteVoterByIdUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}

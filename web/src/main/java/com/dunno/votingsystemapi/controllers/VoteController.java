package com.dunno.votingsystemapi.controllers;

import com.dunno.votingsystemapi.commands.votes.ListAllVotesCommand;
import com.dunno.votingsystemapi.commands.votes.VoteCommand;
import com.dunno.votingsystemapi.dto.VoteRequest;
import com.dunno.votingsystemapi.dto.VoteResponse;
import com.dunno.votingsystemapi.models.Vote;
import com.dunno.votingsystemapi.usecases.votes.ListAllVotesUseCase;
import com.dunno.votingsystemapi.usecases.votes.VoteUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final ListAllVotesUseCase listAllVotesUseCase;
    private final VoteUseCase voteUseCase;

    public VoteController(ListAllVotesUseCase listAllVotesUseCase, VoteUseCase voteUseCase) {
        this.listAllVotesUseCase = listAllVotesUseCase;
        this.voteUseCase = voteUseCase;
    }

    @GetMapping
    public ResponseEntity<List<VoteResponse>> listAllVotes(){
        return ResponseEntity.status(HttpStatus.OK).body(
                listAllVotesUseCase.execute(new ListAllVotesCommand()).stream()
                        .map(vote -> new VoteResponse(vote.getId(), vote.getVoterId(), vote.getCandidateId()))
                        .toList()
        );
    }

    @PostMapping
    public ResponseEntity<VoteResponse> vote(@AuthenticationPrincipal UserDetails userDetails, @RequestBody VoteRequest request){

        VoteCommand command = new VoteCommand(request.candidateId(), userDetails.getUsername());

        Vote vote = voteUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.OK).body(new VoteResponse(vote.getId(), vote.getVoterId(), vote.getCandidateId()));
    }
}

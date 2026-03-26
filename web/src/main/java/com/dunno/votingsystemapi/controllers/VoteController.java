package com.dunno.votingsystemapi.controllers;

import com.dunno.votingsystemapi.commands.votes.ListAllVotesCommand;
import com.dunno.votingsystemapi.dto.VoteResponse;
import com.dunno.votingsystemapi.usecases.votes.ListAllVotesUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final ListAllVotesUseCase listAllVotesUseCase;

    public VoteController(ListAllVotesUseCase listAllVotesUseCase) {
        this.listAllVotesUseCase = listAllVotesUseCase;
    }

    @GetMapping
    public ResponseEntity<List<VoteResponse>> listAllVotes(){
        return ResponseEntity.status(HttpStatus.OK).body(
                listAllVotesUseCase.execute(new ListAllVotesCommand()).stream()
                        .map(vote -> new VoteResponse(vote.getId(), vote.getVoterId(), vote.getCandidateId()))
                        .toList()
        );
    }
}

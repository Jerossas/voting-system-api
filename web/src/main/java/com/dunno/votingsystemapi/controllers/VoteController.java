package com.dunno.votingsystemapi.controllers;

import com.dunno.votingsystemapi.commands.votes.GetVoteStatisticsCommand;
import com.dunno.votingsystemapi.commands.votes.ListAllVotesCommand;
import com.dunno.votingsystemapi.commands.votes.VoteCommand;
import com.dunno.votingsystemapi.dto.CandidateResultResponse;
import com.dunno.votingsystemapi.dto.VoteRequest;
import com.dunno.votingsystemapi.dto.VoteResponse;
import com.dunno.votingsystemapi.dto.VoteStatisticsResponse;
import com.dunno.votingsystemapi.models.Vote;
import com.dunno.votingsystemapi.models.VoteStatistics;
import com.dunno.votingsystemapi.usecases.votes.GetVoteStatisticsUseCase;
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

    private final VoteUseCase voteUseCase;
    private final GetVoteStatisticsUseCase getVoteStatisticsUseCase;

    public VoteController(VoteUseCase voteUseCase, GetVoteStatisticsUseCase getVoteStatisticsUseCase) {
        this.voteUseCase = voteUseCase;
        this.getVoteStatisticsUseCase = getVoteStatisticsUseCase;
    }

    @PostMapping
    public ResponseEntity<VoteResponse> vote(@AuthenticationPrincipal UserDetails userDetails, @RequestBody VoteRequest request){

        VoteCommand command = new VoteCommand(request.candidateId(), userDetails.getUsername());

        Vote vote = voteUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(new VoteResponse(vote.getId(), vote.getVoterId(), vote.getCandidateId()));
    }

    @GetMapping("/statistics")
    public ResponseEntity<VoteStatisticsResponse> getStatistics(){

        GetVoteStatisticsCommand command = new GetVoteStatisticsCommand();

        VoteStatistics statistics = getVoteStatisticsUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.OK).body(new VoteStatisticsResponse(
                statistics.getTotalVotesCast(),
                statistics.getTotalVotersRegistered(),
                statistics.getResults().stream()
                        .map(candidate -> new CandidateResultResponse(
                                candidate.getCandidateId(),
                                candidate.getCandidateName(),
                                candidate.getParty(),
                                candidate.getVotes(),
                                candidate.getPercentage())
                        ).toList()
        ));
    }
}

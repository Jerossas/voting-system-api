package com.dunno.votingsystemapi.controllers;

import com.dunno.votingsystemapi.commands.votes.GetVoteStatisticsCommand;
import com.dunno.votingsystemapi.commands.votes.VoteCommand;
import com.dunno.votingsystemapi.dto.CandidateResultResponse;
import com.dunno.votingsystemapi.dto.VoteRequest;
import com.dunno.votingsystemapi.dto.VoteResponse;
import com.dunno.votingsystemapi.dto.VoteStatisticsResponse;
import com.dunno.votingsystemapi.exceptions.ErrorResponse;
import com.dunno.votingsystemapi.models.Vote;
import com.dunno.votingsystemapi.models.VoteStatistics;
import com.dunno.votingsystemapi.usecases.votes.GetVoteStatisticsUseCase;
import com.dunno.votingsystemapi.usecases.votes.VoteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Votes", description = "Emisión de votos y estadísticas")
@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteUseCase voteUseCase;
    private final GetVoteStatisticsUseCase getVoteStatisticsUseCase;

    public VoteController(VoteUseCase voteUseCase, GetVoteStatisticsUseCase getVoteStatisticsUseCase) {
        this.voteUseCase = voteUseCase;
        this.getVoteStatisticsUseCase = getVoteStatisticsUseCase;
    }

    @Operation(
            summary = "Emitir voto",
            description = "Permite a un votante emitir su voto por un candidato. Cada votante solo puede votar una vez."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Voto emitido exitosamente",
                    content = @Content(schema = @Schema(implementation = VoteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Candidato o votante no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "El votante ya emitió su voto",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Token inválido o rol sin permisos",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<VoteResponse> vote(@AuthenticationPrincipal UserDetails userDetails, @RequestBody VoteRequest request){

        VoteCommand command = new VoteCommand(request.candidateId(), userDetails.getUsername());

        Vote vote = voteUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(new VoteResponse(vote.getId(), vote.getVoterId(), vote.getCandidateId()));
    }

    @Operation(
            summary = "Ver estadísticas de votación",
            description = "Retorna el total de votos emitidos, votantes registrados y el resultado por candidato con porcentajes."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estadísticas calculadas exitosamente",
                    content = @Content(schema = @Schema(implementation = VoteStatisticsResponse.class))),
            @ApiResponse(responseCode = "403", description = "Token inválido o rol sin permisos",
                    content = @Content)
    })
    @GetMapping("/statistics")
    public ResponseEntity<VoteStatisticsResponse> getStatistics(){

        GetVoteStatisticsCommand command = new GetVoteStatisticsCommand();

        VoteStatistics statistics = getVoteStatisticsUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.OK).body(new VoteStatisticsResponse(
                statistics.totalVotesCast(),
                statistics.totalVotersRegistered(),
                statistics.results().stream()
                        .map(candidate -> new CandidateResultResponse(
                                candidate.candidateId(),
                                candidate.candidateName(),
                                candidate.party(),
                                candidate.votes(),
                                candidate.percentage())
                        ).toList()
        ));
    }
}

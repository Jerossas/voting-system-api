package com.dunno.votingsystemapi.controllers;

import com.dunno.votingsystemapi.commands.candidates.GetCandidateByIdCommand;
import com.dunno.votingsystemapi.commands.candidates.ListAllCandidatesCommand;
import com.dunno.votingsystemapi.dto.CandidatePublicResponse;
import com.dunno.votingsystemapi.exceptions.ErrorResponse;
import com.dunno.votingsystemapi.models.Candidate;
import com.dunno.votingsystemapi.usecases.candidates.GetCandidateByIdUseCase;
import com.dunno.votingsystemapi.usecases.candidates.ListAllCandidatesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Candidates", description = "Consulta pública de candidatos")
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

    @Operation(summary = "Listar candidatos", description = "Retorna todos los candidatos sin exponer su email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de candidatos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CandidatePublicResponse.class)))),
            @ApiResponse(responseCode = "403", description = "Token inválido o sin permisos",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<CandidatePublicResponse>> listAllCandidates() {
        return ResponseEntity.ok(
                listAllCandidatesUseCase.execute(new ListAllCandidatesCommand()).stream()
                        .map(c -> new CandidatePublicResponse(c.getId(), c.getFullName(), c.getParty()))
                        .toList()
        );
    }

    @Operation(summary = "Obtener candidato por ID", description = "Retorna un candidato específico sin exponer su email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Candidato encontrado",
                    content = @Content(schema = @Schema(implementation = CandidatePublicResponse.class))),
            @ApiResponse(responseCode = "404", description = "Candidato no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Token inválido o sin permisos",
                    content = @Content)
    })
    @GetMapping("/{candidateId}")
    public ResponseEntity<CandidatePublicResponse> getCandidateById(@PathVariable Long candidateId) {
        Candidate candidate = getCandidateByIdUseCase.execute(new GetCandidateByIdCommand(candidateId));
        return ResponseEntity.ok(new CandidatePublicResponse(candidate.getId(), candidate.getFullName(), candidate.getParty()));
    }
}

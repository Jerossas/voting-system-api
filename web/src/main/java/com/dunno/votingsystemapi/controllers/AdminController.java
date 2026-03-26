package com.dunno.votingsystemapi.controllers;

import com.dunno.votingsystemapi.commands.auth.RegisterCandidateCommand;
import com.dunno.votingsystemapi.commands.auth.RegisterVoterCommand;
import com.dunno.votingsystemapi.commands.candidates.DeleteCandidateByIdCommand;
import com.dunno.votingsystemapi.commands.candidates.GetCandidateByIdCommand;
import com.dunno.votingsystemapi.commands.candidates.ListAllCandidatesCommand;
import com.dunno.votingsystemapi.commands.voters.DeleteVoterByIdCommand;
import com.dunno.votingsystemapi.commands.voters.GetVoterByIdCommand;
import com.dunno.votingsystemapi.commands.voters.ListAllVotersCommand;
import com.dunno.votingsystemapi.commands.votes.ListAllVotesCommand;
import com.dunno.votingsystemapi.dto.*;
import com.dunno.votingsystemapi.exceptions.ErrorResponse;
import com.dunno.votingsystemapi.models.Candidate;
import com.dunno.votingsystemapi.models.Voter;
import com.dunno.votingsystemapi.usecases.auth.RegisterCandidateUseCase;
import com.dunno.votingsystemapi.usecases.auth.RegisterVoterUseCase;
import com.dunno.votingsystemapi.usecases.candidates.DeleteCandidateByIdUseCase;
import com.dunno.votingsystemapi.usecases.candidates.GetCandidateByIdUseCase;
import com.dunno.votingsystemapi.usecases.candidates.ListAllCandidatesUseCase;
import com.dunno.votingsystemapi.usecases.voters.DeleteVoterByIdUseCase;
import com.dunno.votingsystemapi.usecases.voters.GetVoterByIdUseCase;
import com.dunno.votingsystemapi.usecases.voters.ListAllVotersUseCase;
import com.dunno.votingsystemapi.usecases.votes.ListAllVotesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin", description = "Gestión de votantes, candidatos y votos — solo ADMIN")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final RegisterVoterUseCase registerVoterUseCase;
    private final ListAllVotersUseCase listAllVotersUseCase;
    private final GetVoterByIdUseCase getVoterByIdUseCase;
    private final DeleteVoterByIdUseCase deleteVoterByIdUseCase;
    private final RegisterCandidateUseCase registerCandidateUseCase;
    private final ListAllCandidatesUseCase listAllCandidatesUseCase;
    private final GetCandidateByIdUseCase getCandidateByIdUseCase;
    private final DeleteCandidateByIdUseCase deleteCandidateByIdUseCase;
    private final ListAllVotesUseCase listAllVotesUseCase;

    public AdminController(
            RegisterVoterUseCase registerVoterUseCase,
            ListAllVotersUseCase listAllVotersUseCase,
            GetVoterByIdUseCase getVoterByIdUseCase,
            DeleteVoterByIdUseCase deleteVoterByIdUseCase,
            RegisterCandidateUseCase registerCandidateUseCase,
            ListAllCandidatesUseCase listAllCandidatesUseCase,
            GetCandidateByIdUseCase getCandidateByIdUseCase,
            DeleteCandidateByIdUseCase deleteCandidateByIdUseCase,
            ListAllVotesUseCase listAllVotesUseCase
    ) {
        this.registerVoterUseCase = registerVoterUseCase;
        this.listAllVotersUseCase = listAllVotersUseCase;
        this.getVoterByIdUseCase = getVoterByIdUseCase;
        this.deleteVoterByIdUseCase = deleteVoterByIdUseCase;
        this.registerCandidateUseCase = registerCandidateUseCase;
        this.listAllCandidatesUseCase = listAllCandidatesUseCase;
        this.getCandidateByIdUseCase = getCandidateByIdUseCase;
        this.deleteCandidateByIdUseCase = deleteCandidateByIdUseCase;
        this.listAllVotesUseCase = listAllVotesUseCase;
    }

    // VOTERS

    @Operation(summary = "Registrar votante", description = "Crea un nuevo votante en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Votante registrado exitosamente",
                    content = @Content(schema = @Schema(implementation = VoterResponse.class))),
            @ApiResponse(responseCode = "400", description = "Campos inválidos o contraseñas no coinciden",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "El email ya está registrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/voters")
    public ResponseEntity<VoterResponse> registerVoter(@RequestBody CreateVoterRequest request) {
        RegisterVoterCommand command = new RegisterVoterCommand(
                request.fullName(), request.email(), request.password(), request.passwordConfirmation()
        );
        Voter voter = registerVoterUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new VoterResponse(voter.getId(), voter.getFullName(), voter.getEmail().getValue())
        );
    }

    @Operation(summary = "Listar votantes", description = "Retorna todos los votantes registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de votantes",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = VoterResponse.class))))
    })
    @GetMapping("/voters")
    public ResponseEntity<List<VoterResponse>> listAllVoters() {
        return ResponseEntity.ok(
                listAllVotersUseCase.execute(new ListAllVotersCommand()).stream()
                        .map(voter -> new VoterResponse(voter.getId(), voter.getFullName(), voter.getEmail().getValue()))
                        .toList()
        );
    }

    @Operation(summary = "Obtener votante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Votante encontrado",
                    content = @Content(schema = @Schema(implementation = VoterResponse.class))),
            @ApiResponse(responseCode = "404", description = "Votante no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/voters/{voterId}")
    public ResponseEntity<VoterResponse> getVoterById(@PathVariable Long voterId) {
        Voter voter = getVoterByIdUseCase.execute(new GetVoterByIdCommand(voterId));
        return ResponseEntity.ok(
                new VoterResponse(voter.getId(), voter.getFullName(), voter.getEmail().getValue())
        );
    }

    @Operation(summary = "Eliminar votante", description = "Elimina un votante siempre que no haya emitido su voto")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Votante eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Votante no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "El votante ya emitió su voto y no puede ser eliminado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/voters/{voterId}")
    public ResponseEntity<Void> deleteVoterById(@PathVariable Long voterId) {
        deleteVoterByIdUseCase.execute(new DeleteVoterByIdCommand(voterId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // CANDIDATES

    @Operation(summary = "Registrar candidato", description = "Crea un nuevo candidato en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Candidato registrado exitosamente",
                    content = @Content(schema = @Schema(implementation = CandidateResponse.class))),
            @ApiResponse(responseCode = "400", description = "Campos inválidos o contraseñas no coinciden",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "El email ya está registrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/candidates")
    public ResponseEntity<CandidateResponse> registerCandidate(@RequestBody CreateCandidateRequest request) {
        RegisterCandidateCommand command = new RegisterCandidateCommand(
                request.fullName(), request.email(), request.password(), request.passwordConfirmation(), request.party()
        );
        Candidate candidate = registerCandidateUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CandidateResponse(candidate.getId(), candidate.getFullName(), candidate.getParty(), candidate.getEmail().getValue())
        );
    }

    @Operation(summary = "Listar candidatos (Admin)", description = "Retorna todos los candidatos incluyendo su email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de candidatos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CandidateResponse.class))))
    })
    @GetMapping("/candidates")
    public ResponseEntity<List<CandidateResponse>> listAllCandidates() {
        return ResponseEntity.ok(
                listAllCandidatesUseCase.execute(new ListAllCandidatesCommand()).stream()
                        .map(candidate -> new CandidateResponse(candidate.getId(), candidate.getFullName(), candidate.getParty(), candidate.getEmail().getValue()))
                        .toList()
        );
    }

    @Operation(summary = "Obtener candidato por ID (Admin)", description = "Retorna un candidato incluyendo su email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Candidato encontrado",
                    content = @Content(schema = @Schema(implementation = CandidateResponse.class))),
            @ApiResponse(responseCode = "404", description = "Candidato no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/candidates/{candidateId}")
    public ResponseEntity<CandidateResponse> getCandidateById(@PathVariable Long candidateId) {
        Candidate candidate = getCandidateByIdUseCase.execute(new GetCandidateByIdCommand(candidateId));
        return ResponseEntity.ok(
                new CandidateResponse(candidate.getId(), candidate.getFullName(), candidate.getParty(), candidate.getEmail().getValue())
        );
    }

    @Operation(summary = "Eliminar candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Candidato eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Candidato no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/candidates/{candidateId}")
    public ResponseEntity<Void> deleteCandidateById(@PathVariable Long candidateId) {
        deleteCandidateByIdUseCase.execute(new DeleteCandidateByIdCommand(candidateId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // VOTES

    @Operation(summary = "Listar todos los votos", description = "Retorna el historial completo de votos emitidos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de votos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = VoteResponse.class))))
    })
    @GetMapping("/votes")
    public ResponseEntity<List<VoteResponse>> listAllVotes() {
        return ResponseEntity.ok(
                listAllVotesUseCase.execute(new ListAllVotesCommand()).stream()
                        .map(vote -> new VoteResponse(vote.getId(), vote.getVoterId(), vote.getCandidateId()))
                        .toList()
        );
    }
}
package com.dunno.votingsystemapi.controllers;

import com.dunno.votingsystemapi.commands.auth.SignInCommand;
import com.dunno.votingsystemapi.dto.*;
import com.dunno.votingsystemapi.exceptions.ErrorResponse;
import com.dunno.votingsystemapi.models.User;
import com.dunno.votingsystemapi.services.JwtService;
import com.dunno.votingsystemapi.usecases.auth.SignInUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "Autenticación de usuarios")
@RestController
@RequestMapping("/api")
public class AuthController {

    private final SignInUseCase signInUseCase;
    private final JwtService jwtService;

    public AuthController(
            SignInUseCase signInUseCase,
            JwtService jwtService
    ){
        this.signInUseCase = signInUseCase;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Iniciar sesión", description = "Autentica al usuario y retorna un token JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa",
                    content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Ya tienes una sesión activa",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/auth/sign-in")
    public ResponseEntity<TokenResponse> signIn(@RequestBody SignInRequest request){

        SignInCommand command = new SignInCommand(
                request.email(),
                request.password()
        );

        User user = signInUseCase.execute(command);

        TokenResponse response = new TokenResponse(
                jwtService.generate(user)
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

package com.dunno.votingsystemapi.controllers;

import com.dunno.votingsystemapi.commands.auth.SignInCommand;
import com.dunno.votingsystemapi.dto.*;
import com.dunno.votingsystemapi.models.User;
import com.dunno.votingsystemapi.services.JwtService;
import com.dunno.votingsystemapi.usecases.auth.SignInUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

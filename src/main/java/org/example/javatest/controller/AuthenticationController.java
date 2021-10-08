package org.example.javatest.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.javatest.model.UserData;
import org.example.javatest.response.LoginResponse;
import org.example.javatest.response.RegisterResponse;
import org.example.javatest.service.AuthenticationService;
import org.example.javatest.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(path = "/auth")
@Tag(name = "authentication-controller", description = "API for player registration and authentication with user name and password.")
public class AuthenticationController {
    @Autowired
    private AuthenticationService service;

    @ApiOperation(
            value = "Register a new player.",
            notes = "Register a new player. Only registered players are allowed to login and play.",
            tags = {"authentication-controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Player successfully registered."),
            @ApiResponse(code = 400, message = "Missing user name or password in the request."),
            @ApiResponse(code = 422, message = "Duplicated entry. The word/phrase was already scored by this player before."),
            @ApiResponse(code = 500, message = "An unexpected error has occurred. The error has been logged and is being investigated.")}
    )
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody UserData userData) {
        service.register(userData.getUserName(), userData.getPassword());
        return ResponseEntity
                .created(URI.create(userData.getUserName()))
                .body(new RegisterResponse(userData.getUserName()));
    }

    @ApiOperation(
            value = "Player login.",
            notes = "Authorizes the player's password and generates a token. Only authorized players are allowed to play.",
            tags = {"authentication-controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Player successfully logged in."),
            @ApiResponse(code = 401, message = "Invalid password."),
            @ApiResponse(code = 500, message = "An unexpected error has occurred. The error has been logged and is being investigated.")}
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserData userData) {
        Token token = service.login(userData.getUserName(), userData.getPassword());
        return ResponseEntity.ok(LoginResponse.fromToken(token));
    }

}

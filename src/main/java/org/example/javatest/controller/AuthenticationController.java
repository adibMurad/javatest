package org.example.javatest.controller;

import org.example.javatest.model.UserData;
import org.example.javatest.service.AuthenticationService;
import org.example.javatest.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserData userData) {
        try {
            service.register(userData);
            return ResponseEntity.created(URI.create(userData.getUserName())).body(userData.getUserName());
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserData userData) {
        try {
            String token = service.login(userData);
            return ResponseEntity.ok(token);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}

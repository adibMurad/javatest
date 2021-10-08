package org.example.javatest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.javatest.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/game")
@Tag(name = "game-controller", description = "API for the game functionality.")
public class GameController {
    @Autowired
    private GameService service;

    @GetMapping("/{word}")
    public ResponseEntity<String> palindromeScore(@PathVariable String word) {
        try {
            final int score = service.calculateScore(word);
            return ResponseEntity.ok(Integer.toString(score));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

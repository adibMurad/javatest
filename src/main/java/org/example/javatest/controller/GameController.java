package org.example.javatest.controller;

import org.example.javatest.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/game")
public class GameController {
    @Autowired
    private GameService service;

    @GetMapping("/{word}")
    public ResponseEntity<Integer> palindromeScore(@PathVariable String word) {
        final int score = service.calculateScore(word);
        if (score == GameService.INVALID_SCORE) {
            return ResponseEntity.badRequest().body(score);
        }
        return ResponseEntity.ok(score);
    }

}

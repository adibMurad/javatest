package org.example.javatest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/game")
public class GameController {

    @GetMapping("/{word}")
    public ResponseEntity<Integer> palindromeScore(@PathVariable String word) {
        return ResponseEntity.ok(1);
    }

}

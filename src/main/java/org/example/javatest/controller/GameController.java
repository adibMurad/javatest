package org.example.javatest.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.javatest.response.ScoreResponse;
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

    @ApiOperation(
            value = "Score points if the word/phrase is a valid palindrome.",
            notes = "The player will score points if the word/phrase is a valid palindrome, meaning that after removing all non-alphanumeric characters:\n" +
                    "     <li>word/phrase cannot be null, empty or contain only one distinct character.\n" +
                    "     <li>word/phrase must read the same in reverse order.\n\n" +
                    " The player gets score based on the number of unique characters" +
                    " in the word/phrase, discarding whitespaces and punctuations.\n" +
                    " Examples:\n" +
                    "     <li>thala laht - score 4 (t,h,a,l)\n" +
                    "     <li>xoxoxox ox oxo xo!x ox .oxox-oxoxoxoxoxox - score 2 (x, o)\n" +
                    "     <li>Socorram-me, subi no onibus em Marrocos. - score 11 (s, o, c, r, a, m, e, u, b, i, n)\n\n" +
                    "Requires an authorization header <b><tt>Authorization: Bearer <i>token</i></tt></b>.",
            tags = {"game-controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The player scored! Word and its score were successfully recorded."),
            @ApiResponse(code = 400, message = "The submitted word/phrase is not a valid palindrome."),
            @ApiResponse(code = 401, message = "The player is not logged in, its token is invalid or missing."),
            @ApiResponse(code = 422, message = "Duplicated entry. The word/phrase was already scored by this player before."),
            @ApiResponse(code = 500, message = "An unexpected error has occurred. The error has been logged and is being investigated.")}
    )
    @GetMapping("/{word}")
    public ResponseEntity<ScoreResponse> palindromeScore(@PathVariable String word) {
        final int score = service.calculateScore(word);
        return ResponseEntity.ok(new ScoreResponse(score));
    }

}

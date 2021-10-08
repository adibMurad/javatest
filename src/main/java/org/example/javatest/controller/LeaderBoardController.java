package org.example.javatest.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.javatest.model.WordEntry;
import org.example.javatest.response.PlayerBoardEntry;
import org.example.javatest.service.LeaderBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/board")
@Tag(name = "leader-board-controller", description = "API to retrieve player scores.")
public class LeaderBoardController {
    @Autowired
    private LeaderBoardService service;

    @ApiOperation(
            value = "List the scores of the player own submissions.",
            notes = "List the scores of the player ordered from higher to lower score.\n" +
                    "Requires an authorization header <b><tt>Authorization: Bearer <i>token</i></tt></b>.",
            tags = {"leader-board-controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of words and scores ordered from higher to lower score."),
            @ApiResponse(code = 204, message = "The player don't have any score yet."),
            @ApiResponse(code = 401, message = "The player is not logged in, its token is invalid or missing."),
            @ApiResponse(code = 500, message = "An unexpected error has occurred. The error has been logged and is being investigated.")})
    @GetMapping("")
    public ResponseEntity<List<PlayerBoardEntry>> playerBoard() {
        List<PlayerBoardEntry> result = service.getUserBoard();
        if (result.size() > 0)
            return ResponseEntity.ok(result);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(
            value = "List the top 10 scores from all players.",
            notes = "List the top 10 scores from all players, ordered from higher to lower score.<br>Requires an authorization header <b><tt>Authorization: Bearer <i>token</i></tt></b>.",
            tags = {"leader-board-controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of user names, words and scores ordered from higher to lower score."),
            @ApiResponse(code = 204, message = "No submission has been made yet."),
            @ApiResponse(code = 401, message = "The player is not logged in, its token is invalid or missing."),
            @ApiResponse(code = 500, message = "An unexpected error has occurred. The error has been logged and is being investigated.")}
    )
    @GetMapping("/global")
    public ResponseEntity<List<WordEntry>> globalBoard() {
        List<WordEntry> result = service.getGlobalBoard();
        if (result.size() > 0)
            return ResponseEntity.ok(result);
        return ResponseEntity.noContent().build();
    }
}

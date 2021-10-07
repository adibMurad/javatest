package org.example.javatest.controller;

import org.example.javatest.model.WordEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/board")
public class LeaderBoardController {

    @GetMapping("")
    public ResponseEntity<List<WordEntry>> myBoard() {
        return null;
    }

    @GetMapping("/global")
    public ResponseEntity<List<WordEntry>> globalBoard() {
        return null;
    }
}

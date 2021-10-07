package org.example.javatest.controller;

import org.example.javatest.model.WordEntry;
import org.example.javatest.service.LeaderBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/board")
public class LeaderBoardController {
    @Autowired
    private LeaderBoardService service;

    @GetMapping("")
    public ResponseEntity<List<WordEntry>> myBoard() {
        List<WordEntry> result = service.getUserBoard();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/global")
    public ResponseEntity<List<WordEntry>> globalBoard() {
        List<WordEntry> result = service.getGlobalBoard();
        return ResponseEntity.ok(result);
    }
}

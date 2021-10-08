package org.example.javatest.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.javatest.auth.LoggedUser;
import org.example.javatest.db.WordRepository;
import org.example.javatest.model.WordEntry;
import org.example.javatest.response.PlayerBoardEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class LeaderBoardService {
    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private WordRepository wordRepository;

    @Value("${game.board.size}")
    private int gameBoardSize;

    public List<PlayerBoardEntry> getUserBoard() {
        log.info("{} got own scores", loggedUser.getUserName());
        List<WordEntry> words = wordRepository.findAllByUserName(loggedUser.getUserName());
        return words.stream().map(PlayerBoardEntry::new).collect(Collectors.toList());
    }

    public List<WordEntry> getGlobalBoard() {
        List<WordEntry> board = wordRepository.findAll().stream().limit(gameBoardSize).collect(Collectors.toList());
        log.info("{} got global scores", loggedUser.getUserName());
        return board;
    }
}

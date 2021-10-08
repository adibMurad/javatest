package org.example.javatest.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.javatest.auth.LoggedUser;
import org.example.javatest.db.WordRepository;
import org.example.javatest.exception.GameServiceException;
import org.example.javatest.game.Palindrome;
import org.example.javatest.model.WordEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class GameService {

    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private WordRepository wordRepository;

    private void saveScore(String word, int score) {
        final WordEntry entry = new WordEntry(loggedUser.getUserName(), word, score);
        try {
            wordRepository.save(entry);
        } catch (DataIntegrityViolationException e) {
            throw new GameServiceException(String.format("Word '%s' already scored by the player.", entry.getWord()), e);
        }
    }

    public int calculateScore(String word) {
        int score = Palindrome.fromString(word).score();
        saveScore(word, score);
        log.info("{} scored {}", loggedUser.getUserName(), score);
        return score;
    }

}

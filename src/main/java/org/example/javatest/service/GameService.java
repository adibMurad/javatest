package org.example.javatest.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.javatest.db.WordRepository;
import org.example.javatest.game.Palindrome;
import org.example.javatest.model.WordEntry;
import org.example.javatest.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class GameService {

    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private WordRepository wordRepository;

    private void saveScore(String word, int score) {
        final WordEntry entry = new WordEntry(loggedUser.getUserName(), word, score);
        wordRepository.save(entry);
    }

    public int calculateScore(String word) {
        int score = Palindrome.fromString(word).score();
        saveScore(word, score);
        return score;
    }

}

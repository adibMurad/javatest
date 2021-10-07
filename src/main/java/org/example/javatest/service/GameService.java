package org.example.javatest.service;

import org.example.javatest.db.WordRepository;
import org.example.javatest.model.WordEntry;
import org.example.javatest.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class GameService {
    public static final int INVALID_SCORE = -1;

    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private WordRepository wordRepository;

    private String onlyLetters(String st) {
        return st.replaceAll("\\s+", "").toLowerCase();
    }

    private void saveScore(String word, int score) {
        final WordEntry entry = WordEntry.builder().userName(loggedUser.getUserName()).word(word).score(score).build();
        wordRepository.save(entry);
    }

    private boolean isPalindrome(String word) {
        String straight = onlyLetters(word);
        StringBuffer reverse = new StringBuffer(straight).reverse();
        return (reverse.toString()).equals(straight);
    }

    public int calculateScore(String word) {
        if (!StringUtils.hasText(word) || !isPalindrome(word)) {
            return INVALID_SCORE;
        }
        int score =  word.length();
        saveScore(word, score);
        return score;
    }

}

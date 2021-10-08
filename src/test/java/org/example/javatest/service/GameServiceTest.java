package org.example.javatest.service;

import org.example.javatest.application.JavatestApplication;
import org.example.javatest.db.WordRepository;
import org.example.javatest.model.WordEntry;
import org.example.javatest.util.LoggedUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JavatestApplication.class)
public class GameServiceTest {
    private static final String USERNAME = "userTest";
    private static final String VALID_WORD = "  Aca !BaC: a";

    @Autowired
    private WordRepository wordRepository;
    private GameService service;

    @BeforeEach
    public void before() {
        LoggedUser loggedUser = new LoggedUser();
        loggedUser.setUserName(USERNAME);
        service = new GameService(loggedUser, wordRepository);
    }

    @Test
    public void testScoreSaved() {
        assertEquals(3, service.calculateScore(VALID_WORD));
        List<WordEntry> words = wordRepository.findAllByUserName(USERNAME);
        assertEquals(1, words.size());
        assertEquals(VALID_WORD, words.get(0).getWord());
        assertEquals(USERNAME, words.get(0).getUserName());
        assertEquals(3, words.get(0).getScore());
    }

    @Test
    public void testScoreNotSaved() {
        assertThrows(IllegalArgumentException.class, () -> service.calculateScore(""));
        assertEquals(0, wordRepository.findAllByUserName(USERNAME).size());
    }

}

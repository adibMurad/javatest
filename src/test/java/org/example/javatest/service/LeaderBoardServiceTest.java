package org.example.javatest.service;

import org.example.javatest.application.JavatestApplication;
import org.example.javatest.auth.LoggedUser;
import org.example.javatest.db.UserRepository;
import org.example.javatest.db.WordRepository;
import org.example.javatest.game.Palindrome;
import org.example.javatest.model.UserData;
import org.example.javatest.model.WordEntry;
import org.example.javatest.response.PlayerBoardEntry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JavatestApplication.class)
@TestPropertySource("classpath:application.properties")
public class LeaderBoardServiceTest {
    private static final int GAME_BOARD_SIZE = 10;

    private static final String USER1 = "userTest1";
    private static final String USER2 = "userTest2";
    private static final String PASSWORD = "password";
    private static final String WORD_HIGHER_SCORE = "Socorram-me, subi no onibus em Marrocos.";
    private static final String WORD_LOWER_SCORE = "$   xoxoxox ox oxo xo!x ox .oxox-oxoxoxoxoxox   .";
    private static final String WORD2 = "Luza Rocelina, a namorada do Manuel, leu na moda da romana: 'anil e cor azul'.";
    private static final String[] VALID_WORDS = {
            "  Aca !BaC: a",
            WORD_HIGHER_SCORE,
            "thala laht",
            WORD_LOWER_SCORE
    };

    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private UserRepository userRepository;
    private LeaderBoardService service;

    @BeforeEach
    public void before() {
        service = new LeaderBoardService(new LoggedUser(USER1), wordRepository, GAME_BOARD_SIZE);

        UserData userData = new UserData(USER1, PASSWORD);
        userRepository.save(userData);

        Arrays.stream(VALID_WORDS).forEach(word -> {
            WordEntry wordEntry = new WordEntry(USER1, word, Palindrome.fromString(word).score());
            wordRepository.save(wordEntry);
        });

        userRepository.save(new UserData(USER2, PASSWORD));
        wordRepository.save(new WordEntry(USER2, WORD2, Palindrome.fromString(WORD2).score()));
    }

    @AfterEach
    public void after() {
        wordRepository.deleteAllByUserName(USER1);
        wordRepository.deleteAllByUserName(USER2);
        userRepository.deleteById(USER1);
        userRepository.deleteById(USER2);
    }

    @Transactional
    @Test
    public void testUserBoard() {
        List<PlayerBoardEntry> list = service.getUserBoard();
        assertEquals(VALID_WORDS.length, list.size());
        assertEquals(WORD_HIGHER_SCORE, list.get(0).getWord());
        assertEquals(WORD_LOWER_SCORE, list.get(list.size() - 1).getWord());
    }

    @Transactional
    @Test
    public void testGlobalBoard() {
        List<WordEntry> list = service.getGlobalBoard();
        assertEquals(VALID_WORDS.length + 1, list.size());
        assertEquals(WORD2, list.get(0).getWord());
        assertEquals(WORD_LOWER_SCORE, list.get(list.size() - 1).getWord());
    }

}

package org.example.javatest.response;

import lombok.Data;
import org.example.javatest.model.WordEntry;

@Data
public class PlayerBoardEntry {
    private String word;
    private int score;

    public PlayerBoardEntry(WordEntry wordEntry) {
        this.word = wordEntry.getWord();
        this.score = wordEntry.getScore();
    }
}

package org.example.javatest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "words")
public class WordEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    @Column(name = "user_name")
    private String userName;
    private String word;
    private int score;

    public WordEntry(String userName, String word, int score) {
        this.userName = userName;
        this.word = word;
        this.score = score;
    }
}

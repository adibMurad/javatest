package org.example.javatest.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.javatest.db.WordRepository;
import org.example.javatest.model.WordEntry;
import org.example.javatest.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class LeaderBoardService {
    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private WordRepository wordRepository;

    public List<WordEntry> getUserBoard() {
        return wordRepository.findAllByUserName(loggedUser.getUserName());
    }

    public List<WordEntry> getGlobalBoard() {
        return wordRepository.findAll();
    }
}

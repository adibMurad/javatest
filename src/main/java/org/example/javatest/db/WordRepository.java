package org.example.javatest.db;

import org.example.javatest.model.WordEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WordRepository extends CrudRepository<WordEntry, String> {
    @Query("from WordEntry where userName = :userName order by score desc")
    List<WordEntry> findAllByUserName(String userName);

    @Query("from WordEntry order by score desc")
    List<WordEntry> findAll();
}

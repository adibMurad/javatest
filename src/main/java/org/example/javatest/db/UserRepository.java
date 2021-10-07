package org.example.javatest.db;

import org.example.javatest.model.UserData;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserData, String> {
    UserData findByUserName(String userName);
}

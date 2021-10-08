package org.example.javatest.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.javatest.db.UserRepository;
import org.example.javatest.model.UserData;
import org.example.javatest.util.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenHelper tokenHelper;

    String encodePassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
    }

    public void register(String userName, String password) throws ServiceException {
        if (userRepository.findByUserName(userName) != null) {
            throw new ServiceException("User name already exists.");
        }
        UserData userData = new UserData(userName, password);
        userData.setPassword(encodePassword(password));
        userRepository.save(userData);
    }

    public String login(String userName, String password) throws ServiceException {
        UserData foundUser = userRepository.findByUserName(userName);
        if (foundUser == null) {
            throw new ServiceException("User name does not exist.");
        }
        if (!foundUser.getPassword().equals(encodePassword(password))) {
            throw new ServiceException("Invalid password.");
        }
        return tokenHelper.create(userName);
    }
}

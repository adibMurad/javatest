package org.example.javatest.service;

import org.example.javatest.db.UserRepository;
import org.example.javatest.model.UserData;
import org.example.javatest.util.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenHelper tokenHelper;

    private String encodePassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
    }

    public void register(UserData userData) throws ServiceException {
        if (userRepository.findByUserName(userData.getUserName()) != null) {
            throw new ServiceException("User name already exists.");
        }
        userData.setPassword(encodePassword(userData.getPassword()));
        userRepository.save(userData);
    }

    public String login(UserData userData) throws ServiceException {
        UserData foundUser = userRepository.findByUserName(userData.getUserName());
        if (foundUser == null) {
            throw new ServiceException("User name does not exist.");
        }
        if (!foundUser.getPassword().equals(encodePassword(userData.getPassword()))) {
            throw new ServiceException("Invalid password.");
        }
        return tokenHelper.create(userData.getUserName());
    }
}

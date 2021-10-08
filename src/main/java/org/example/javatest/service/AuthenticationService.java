package org.example.javatest.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.javatest.db.UserRepository;
import org.example.javatest.exception.CreationException;
import org.example.javatest.exception.UnauthorizedException;
import org.example.javatest.model.UserData;
import org.example.javatest.token.Token;
import org.example.javatest.token.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenHelper tokenHelper;

    String encodePassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
    }

    public void register(String userName, String password) throws CreationException {
        if (userRepository.findByUserName(userName) != null) {
            throw new CreationException("User name already exists.");
        }
        UserData userData = new UserData(userName, password);
        userData.setPassword(encodePassword(password));
        userRepository.save(userData);
        log.info("{} registered.", userName);
    }

    public Token login(String userName, String password) throws CreationException {
        UserData foundUser = userRepository.findByUserName(userName);
        if (foundUser == null) {
            throw new UnauthorizedException("User name does not exist.");
        }
        if (!foundUser.getPassword().equals(encodePassword(password))) {
            throw new UnauthorizedException("Invalid password.");
        }
        log.info("{} logged in.", userName);
        return tokenHelper.create(userName);
    }
}

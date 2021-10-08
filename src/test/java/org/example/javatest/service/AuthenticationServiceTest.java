package org.example.javatest.service;

import org.example.javatest.application.JavatestApplication;
import org.example.javatest.db.UserRepository;
import org.example.javatest.exception.CreationException;
import org.example.javatest.exception.UnauthorizedException;
import org.example.javatest.model.UserData;
import org.example.javatest.token.Token;
import org.example.javatest.token.TokenHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JavatestApplication.class)
@TestPropertySource("classpath:application.properties")
public class AuthenticationServiceTest {
    private static final String USERNAME = "userTest";
    private static final String PASSWORD = "password";
    private static final int TOKEN_EXPIRATION_SECONDS = 300;
    private static final String TOKEN_ALGORITHM = "HS256";
    private static final String TOKEN_SECRET = "cEyNlCLPoP9DFZAsOIaDeVnknxy6yciyEFcQ5LvhotKSi9aVH8oKTzU4Wj0ij6GM";

    @Autowired
    private UserRepository userRepository;
    private AuthenticationService service;

    @BeforeEach
    public void before() {
        service = new AuthenticationService(userRepository, new TokenHelper(TOKEN_ALGORITHM, TOKEN_SECRET, TOKEN_EXPIRATION_SECONDS));
    }

    @AfterEach
    public void after() {
        userRepository.deleteById(USERNAME);
    }

    @Test
    public void testRegister() {
        service.register(USERNAME, PASSWORD);
        UserData retrievedData = userRepository.findByUserName(USERNAME);
        assertEquals(USERNAME, retrievedData.getUserName());
        assertEquals(service.encodePassword(PASSWORD), retrievedData.getPassword());

        assertThrows(CreationException.class, () -> service.register(USERNAME, PASSWORD));
    }

    @Test
    public void testLogin() {
        assertThrows(UnauthorizedException.class, () -> service.login(USERNAME, "X"));
        assertThrows(UnauthorizedException.class, () -> service.login("x", PASSWORD));
        assertThrows(UnauthorizedException.class, () -> service.login(USERNAME, PASSWORD));

        service.register(USERNAME, PASSWORD);
        Token token = service.login(USERNAME, PASSWORD);
        assertNotNull(token);
    }
}

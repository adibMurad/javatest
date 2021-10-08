package org.example.javatest.service;

import org.example.javatest.application.JavatestApplication;
import org.example.javatest.db.UserRepository;
import org.example.javatest.model.UserData;
import org.example.javatest.util.TokenHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JavatestApplication.class)
public class AuthenticationServiceTest {
    private static final String USERNAME = "userTest";
    private static final String PASSWORD = "password";

    @Autowired
    private UserRepository userRepository;
    private AuthenticationService service;

    @BeforeEach
    public void before() {
        service = new AuthenticationService(userRepository, new TokenHelper());
    }

    @AfterEach
    public void after() {
        userRepository.deleteById(USERNAME);
    }

    @Test
    public void testRegister() throws ServiceException {
        service.register(USERNAME, PASSWORD);
        UserData retrievedData = userRepository.findByUserName(USERNAME);
        assertEquals(USERNAME, retrievedData.getUserName());
        assertEquals(service.encodePassword(PASSWORD), retrievedData.getPassword());

        assertThrows(ServiceException.class, () -> service.register(USERNAME, PASSWORD));
    }

    @Test
    public void testLogin() throws ServiceException {
        assertThrows(ServiceException.class, () -> service.login(USERNAME, "X"));
        assertThrows(ServiceException.class, () -> service.login("x", PASSWORD));
        assertThrows(ServiceException.class, () -> service.login(USERNAME, PASSWORD));

        service.register(USERNAME, PASSWORD);
        String token = service.login(USERNAME, PASSWORD);
        assertNotNull(token);
    }
}

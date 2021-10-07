package org.example.javatest.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TokenHelperTests {
    private static final String USER_NAME_TEST = "johndoe";

    private String tokenOk;
    private String tokenNoUser;
    private TokenHelper tokenHelper;

    @BeforeEach
    public void before() {
        tokenHelper = new TokenHelper();
        tokenNoUser = tokenHelper.create(null);
        tokenOk = tokenHelper.create(USER_NAME_TEST);
    }

    @Test
    public void testIsValid() {
        assertFalse(tokenHelper.isValid(null));
        assertFalse(tokenHelper.isValid(""));
        assertFalse(tokenHelper.isValid(tokenNoUser));
        assertTrue(tokenHelper.isValid(tokenOk));
    }

    @Test
    public void testGetUserName() {
        assertNull(tokenHelper.getUserName("a"));
        assertNull(tokenHelper.getUserName(tokenNoUser));
        assertEquals("johndoe", tokenHelper.getUserName(tokenOk));
    }

    @Test
    public void testCreate() {
        String token = tokenHelper.create("johndoe");
    }

}

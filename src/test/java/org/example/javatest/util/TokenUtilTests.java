package org.example.javatest.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.javatest.util.TokenUtil.*;
import static org.junit.jupiter.api.Assertions.*;

public class TokenUtilTests {
    private static final String USER_NAME_TEST = "johndoe";

    private String tokenOk;
    private String tokenNoUser;

    @BeforeEach
    public void before() {
        tokenNoUser = create(null);
        tokenOk = create(USER_NAME_TEST);
    }

    @Test
    public void testIsValid() {
        assertFalse(isValid(null));
        assertFalse(isValid(""));
        assertFalse(isValid(tokenNoUser));
        assertTrue(isValid(tokenOk));
    }

    @Test
    public void testGetUserName() {
        assertNull(getUserName("a"));
        assertNull(getUserName(tokenNoUser));
        assertEquals("johndoe", getUserName(tokenOk));
    }

    @Test
    public void testCreate() {
        String token = create("johndoe");
    }

}

package org.example.javatest.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TokenHelperTests {
    private static final String USER_NAME_TEST = "johndoe";
    private static final int TOKEN_EXPIRATION_SECONDS = 300;
    private static final String TOKEN_ALGORITHM = "HS256";
    private static final String TOKEN_SECRET = "cEyNlCLPoP9DFZAsOIaDeVnknxy6yciyEFcQ5LvhotKSi9aVH8oKTzU4Wj0ij6GM";

    private String tokenOk;
    private String tokenNoUser;
    private TokenHelper tokenHelper;

    @BeforeEach
    public void before() {
        tokenHelper = new TokenHelper(TOKEN_ALGORITHM, TOKEN_SECRET, TOKEN_EXPIRATION_SECONDS);
        tokenNoUser = tokenHelper.create(null).getCompact();
        tokenOk = tokenHelper.create(USER_NAME_TEST).getCompact();
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
        String token = tokenHelper.create("johndoe").getCompact();
    }

}

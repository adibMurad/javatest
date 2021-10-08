package org.example.javatest.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PalindromeTest {
    private static final String VALID_WORD = "  Aca !BaC: a";
    private static final String VALID_LETTERS = "acabaca";

    @Test
    public void testFromStringException() {
        assertThrows(IllegalArgumentException.class, () -> Palindrome.fromString(null));
        assertThrows(IllegalArgumentException.class, () -> Palindrome.fromString(""));
        assertThrows(IllegalArgumentException.class, () -> Palindrome.fromString("      "));
        assertThrows(IllegalArgumentException.class, () -> Palindrome.fromString("    .   .  x .. xx!! x"));
    }

    @Test
    public void testFields() {
        Palindrome palindrome = Palindrome.fromString(VALID_WORD);
        assertEquals(VALID_WORD, palindrome.getWord());
        assertEquals(VALID_LETTERS, palindrome.getLetters());
    }

    @Test
    public void testScore() {
        assertEquals(3, Palindrome.fromString(VALID_WORD).score());
        assertEquals(11, Palindrome.fromString("Socorram-me, subi no onibus em Marrocos.").score());
        assertEquals(4, Palindrome.fromString("thala laht").score());
        assertEquals(2, Palindrome.fromString("$   xoxoxox ox oxo xo!x ox .oxox-oxoxoxoxoxox   .").score());
    }

}

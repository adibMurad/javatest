package org.example.javatest.game;

import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Game class that represents a valid palindrome.
 */
@Getter
public class Palindrome {
    public static final int INVALID_SCORE = -1;

    private final String word;
    private final String letters;

    public Palindrome(String word, String letters) {
        this.word = word;
        this.letters = letters;
    }

    /**
     * Builds a Palindrome instance if {@link #word} is a valid palindrome.
     * That means that after removing all non-alphanumeric characters:
     * - {@link #word} cannot be null, empty or contain only one distinct character.
     * - {@link #word} must read the same in reverse order.
     *
     * @param word the palindrome candidate
     * @return a new valid Palindrome instance
     */
    public static Palindrome fromString(String word) {
        final String letters = onlyLetters(word);
        if (notPalindrome(letters)) {
            throw new IllegalArgumentException(String.format("%s is not a palindrome", word));
        }
        return new Palindrome(word, letters);
    }

    /**
     * Calculates the score of this palindrome word counting the number of unique letters.
     * Examples:
     * - thala laht - score 4 (t,h,a,l)
     * - xoxoxox ox oxo xo!x ox .oxox-oxoxoxoxoxox - score 2 (x, o)
     *
     * @return the score of {@link #word}
     */
    public int score() {
        return score(letters);
    }

    private static int score(String letters) {
        Set<Character> chars = new HashSet<>();
        for (char ch : letters.toCharArray())
            chars.add(ch);
        return chars.size();
    }

    private static String onlyLetters(String st) {
        return st != null ? st.replaceAll("[\\W\\d]+", "").toLowerCase() : "";
    }

    private static boolean notPalindrome(String letters) {
        if (!StringUtils.hasText(letters)) {
            return true;
        }
        if (score(letters) < 2) {
            return true;
        }
        StringBuffer reverse = new StringBuffer(letters).reverse();
        return !(reverse.toString()).equals(letters);
    }

}

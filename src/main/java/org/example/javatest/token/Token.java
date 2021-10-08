package org.example.javatest.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class Token {
    private final String compact;
    private final Date expiration;
}

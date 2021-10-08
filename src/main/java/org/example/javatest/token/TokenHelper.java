package org.example.javatest.token;

import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class TokenHelper {
    @Value("${token.algorithm}")
    private String algorithm;
    @Value("${token.secret}")
    private String secret;
    @Value("${token.expiration.seconds}")
    private int expirationSeconds;

    public boolean isValid(String token) {
        return StringUtils.hasText(getUserName(token));
    }

    public String getUserName(String token) {
        try {
            JwtParser parser = Jwts.parser().setSigningKey(Base64.encodeBase64(secret.getBytes(StandardCharsets.UTF_8)));
            Jws<Claims> jws = parser.parseClaimsJws(token);
            return jws.getBody().getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    public Token create(String userName) {
        Instant now = Instant.now();
        Date expiration = Date.from(now.plusSeconds(expirationSeconds));
        String compact = Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(Date.from(now))
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.forName(algorithm), Base64.encodeBase64(secret.getBytes(StandardCharsets.UTF_8)))
                .compact();
        return new Token(compact, expiration);
    }
}

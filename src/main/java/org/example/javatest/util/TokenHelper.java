package org.example.javatest.util;

import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

public class TokenHelper {
    private static final SignatureAlgorithm ALGORITHM = HS256;
    private static final String SECRET_KEY = "cEyNlCLPoP9DFZAsOIaDeVnknxy6yciyEFcQ5LvhotKSi9aVH8oKTzU4Wj0ij6GM";
    private static final int EXPIRATION_SECONDS = 300;

    public boolean isValid(String token) {
        return StringUtils.hasText(getUserName(token));
    }

    public String getUserName(String token) {
        try {
            JwtParser parser = Jwts.parser().setSigningKey(Base64.encodeBase64(SECRET_KEY.getBytes(StandardCharsets.UTF_8)));
            Jws<Claims> jws = parser.parseClaimsJws(token);
            return jws.getBody().getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    public String create(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setExpiration(Date.from(Instant.now().plusSeconds(EXPIRATION_SECONDS)))
                .signWith(ALGORITHM, Base64.encodeBase64(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }
}

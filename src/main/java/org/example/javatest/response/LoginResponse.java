package org.example.javatest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.javatest.token.Token;

import java.util.Date;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse {
    @JsonProperty("access_token")
    private final String accessToken;
    @JsonProperty("token_type")
    private final String tokenType = "bearer";
    @JsonProperty("expires_in")
    private final Date expiresIn;

    public static LoginResponse fromToken(Token token) {
        return new LoginResponse(token.getCompact(), token.getExpiration());
    }
}

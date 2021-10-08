package org.example.javatest.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.javatest.token.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class AuthorizationFilter implements Filter {
    @Autowired
    private final LoggedUser loggedUser;
    @Autowired
    private final TokenHelper tokenHelper;

    private void sendError(HttpServletResponse resp, String msg) throws IOException {
        log.error(msg);
        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, msg);
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String auth = req.getHeader("Authorization");
        String token = auth != null ? auth.substring("Bearer".length()).trim() : null;
        if (token == null) {
            sendError(resp, "Token required. Please provide a header 'Authorization: Bearer <token>'.");
            return;
        }
        if (!tokenHelper.isValid(token)) {
            sendError(resp, "Invalid token");
            return;
        }
        final String userName = tokenHelper.getUserName(token);
        log.info("{} made a request.", userName);
        loggedUser.setUserName(userName);
        chain.doFilter(request, response);
    }

}

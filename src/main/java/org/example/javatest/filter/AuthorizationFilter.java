package org.example.javatest.filter;

import lombok.AllArgsConstructor;
import org.example.javatest.util.LoggedUser;
import org.example.javatest.util.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class AuthorizationFilter implements Filter {
    @Autowired
    private final LoggedUser loggedUser;
    @Autowired
    private final TokenHelper tokenHelper;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String auth = req.getHeader("Authorization");
        String token = auth.substring("Bearer".length()).trim();

        if (!tokenHelper.isValid(token)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        loggedUser.setUserName(tokenHelper.getUserName(token));
        chain.doFilter(request, response);
    }

}

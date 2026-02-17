package com.priyakdey.backend.security.filter;


import com.priyakdey.backend.security.TokenHandler;
import com.priyakdey.backend.security.config.SessionPayload;
import com.priyakdey.backend.security.config.SessionTokenProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Priyak Dey
 */
@Component
public class SessionTokenFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            SessionTokenFilter.class);

    private final TokenHandler<SessionPayload> sessionTokenHandler;
    private final SessionTokenProperties sessionTokenProperties;

    private static final Set<String> WHITE_LIST = Set.of(
            "/api/v1/auth/google/login", "/api/v1/auth/google/callback"
    );

    public SessionTokenFilter(TokenHandler<SessionPayload> sessionTokenHandler,
                              SessionTokenProperties sessionTokenProperties) {
        this.sessionTokenHandler = sessionTokenHandler;
        this.sessionTokenProperties = sessionTokenProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (WHITE_LIST.contains(uri)) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String sessionToken = null;
        for (Cookie cookie : cookies) {
            if (Objects.equals(cookie.getName(), "session-token")) {
                sessionToken = cookie.getValue();
                break;
            }
        }

        if (sessionToken == null || sessionToken.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String issuer = sessionTokenProperties.getIssuer();
        List<String> aud = List.of("LATER");
        int leeway = sessionTokenProperties.getLeeway();
        try {
            SessionPayload sessionPayload =
                    sessionTokenHandler.decode(sessionToken, issuer, aud, leeway);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(sessionPayload.id(), null, List.of());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception e) {
            LOGGER.error("Invalid session token. Logging out user. ", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}

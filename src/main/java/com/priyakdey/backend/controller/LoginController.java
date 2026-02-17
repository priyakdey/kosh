package com.priyakdey.backend.controller;

import com.priyakdey.backend.exception.AuthException;
import com.priyakdey.backend.security.StateTokenCache;
import com.priyakdey.backend.security.TokenHandler;
import com.priyakdey.backend.security.config.SessionPayload;
import com.priyakdey.backend.security.config.SessionTokenProperties;
import com.priyakdey.backend.security.config.StatePayload;
import com.priyakdey.backend.security.config.StateTokenProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Priyak Dey
 */
public abstract class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            LoginController.class);

    private final ClientRegistration clientRegistration;

    private final TokenHandler<StatePayload> statePayloadTokenHandler;
    private final TokenHandler<SessionPayload> sessionPayloadTokenHandler;

    private final StateTokenProperties stateTokenProperties;
    private final SessionTokenProperties sessionTokenProperties;

    private final StateTokenCache stateTokenCache;

    protected LoginController(ClientRegistration clientRegistration,
                              TokenHandler<StatePayload> statePayloadTokenHandler,
                              TokenHandler<SessionPayload> sessionPayloadTokenHandler,
                              StateTokenProperties stateTokenProperties,
                              SessionTokenProperties sessionTokenProperties,
                              StateTokenCache stateTokenCache) {
        this.sessionPayloadTokenHandler = sessionPayloadTokenHandler;
        this.sessionTokenProperties = sessionTokenProperties;
        if (clientRegistration == null) {
            // TODO: check this error message
            throw new IllegalArgumentException("Google provider is not configured");
        }
        this.clientRegistration = clientRegistration;
        this.statePayloadTokenHandler = statePayloadTokenHandler;
        this.stateTokenProperties = stateTokenProperties;
        this.stateTokenCache = stateTokenCache;
    }


    public abstract ResponseEntity<Void> login();

    public abstract ResponseEntity<Void> callback(String code, String state);

    protected URI buildAuthUri() {
        String authUri = clientRegistration.getProviderDetails().getAuthorizationUri();
        String clientId = clientRegistration.getClientId();

        String state = generateState();

        String redirectUri = clientRegistration.getRedirectUri();
        String responseType = "code";
        String prompt = "consent";
        String scope = String.join(" ", clientRegistration.getScopes());

        Map<String, Object> params = Map.of(
                "state", state,
                "redirect_uri", redirectUri,
                "client_id", clientId,
                "response_type", responseType,
                "scope", scope,
                "prompt", prompt
        );

        return generateUriWithParams(authUri, params);
    }

    protected URI buildAccessTokenUri(String code) {
        String tokenUri = clientRegistration.getProviderDetails().getTokenUri();
        String clientId = clientRegistration.getClientId();
        String clientSecret = clientRegistration.getClientSecret();
        String redirectUri = clientRegistration.getRedirectUri();

        Map<String, Object> params = Map.of(
                "code", code,
                "client_id", clientId,
                "client_secret", clientSecret,
                "redirect_uri", redirectUri,
                "grant_type", "authorization_code"
        );

        return generateUriWithParams(tokenUri, params);
    }

    protected String buildUserInfoUri() {
        return clientRegistration.getProviderDetails().getUserInfoEndpoint().getUri();
    }

    protected void validateState(String state) {
        StatePayload statePayloadFromCache = stateTokenCache.get(state);
        if (statePayloadFromCache == null) {
            throw new AuthException("Received invalid state token");
        }

        String issuer = stateTokenProperties.getIssuer();
        List<String> aud = stateTokenProperties.getAud();
        int leeway = stateTokenProperties.getLeeway();
        try {
            StatePayload decodedStatePayload =
                    statePayloadTokenHandler.decode(state, issuer, aud, leeway);

            if (!decodedStatePayload.equals(statePayloadFromCache)) {
                throw new AuthException("Mismatch of received and generated state token");
            }

        } catch (Exception e) {
            throw new AuthException(e);
        }
    }

    private String generateState() {
        String issuer = stateTokenProperties.getIssuer();
        List<String> aud = stateTokenProperties.getAud();
        Instant iat = Instant.now();
        int ttl = stateTokenProperties.getTtl();
        Instant eat = iat.plus(ttl, ChronoUnit.SECONDS);

        String nonce = UUID.randomUUID().toString();

        StatePayload statePayload = new StatePayload(nonce, eat.truncatedTo(ChronoUnit.SECONDS));
        String state = statePayloadTokenHandler.sign(statePayload, issuer, aud, iat, eat);

        stateTokenCache.put(state, statePayload);

        return state;
    }

    protected ResponseCookie generateSessionCookie(String id, String name,
                                                   String email) {
        String issuer = sessionTokenProperties.getIssuer();
        int ttl = sessionTokenProperties.getTtl();

        Instant iat = Instant.now();
        Instant exp = iat.plus(ttl, ChronoUnit.SECONDS);

        SessionPayload sessionPayload = new SessionPayload(id, name, email);
        String sessionToken = sessionPayloadTokenHandler.sign(sessionPayload, issuer,
                List.of("LATER"), iat, exp);        // TODO

        return ResponseCookie.from("session-token", sessionToken)
                .path("/")
                .httpOnly(true)
                .maxAge(ttl)
                .sameSite("Lax")    // TODO: env driven
                .secure(false)      // TODO: env driven
                .build();
    }

    private URI generateUriWithParams(String uri, Map<String, Object> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri);
        params.forEach(builder::queryParam);
        return builder.build().toUri();
    }

}

package com.priyakdey.backend.controller;


import com.priyakdey.backend.exception.AuthException;
import com.priyakdey.backend.model.integration.google.GoogleAccessToken;
import com.priyakdey.backend.model.integration.google.GoogleUser;
import com.priyakdey.backend.security.StateTokenCache;
import com.priyakdey.backend.security.TokenHandler;
import com.priyakdey.backend.security.config.SessionPayload;
import com.priyakdey.backend.security.config.SessionTokenProperties;
import com.priyakdey.backend.security.config.StatePayload;
import com.priyakdey.backend.security.config.StateTokenProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.net.URI;

/**
 * @author Priyak Dey
 */
@RestController
@RequestMapping(path = "/api/v1/auth/google")
public class GoogleLoginController extends LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleLoginController.class);

    private final RestClient restClient;

    private final String uiHomePageUrl;

    // private final ProfileService profileService;

    public GoogleLoginController(ClientRegistrationRepository clientRegistrationRepository,
                                 TokenHandler<StatePayload> statePayloadTokenHandler,
                                 StateTokenProperties stateTokenProperties,
                                 StateTokenCache stateTokenCache,
                                 RestClient restClient,
                                 @Value("${app.ui.home}") String uiHomePageUrl,
                                 TokenHandler<SessionPayload> sessionPayloadTokenHandler,
                                 SessionTokenProperties sessionTokenProperties) {
        super(clientRegistrationRepository.findByRegistrationId("google"),
                statePayloadTokenHandler, sessionPayloadTokenHandler,
                stateTokenProperties, sessionTokenProperties,
                stateTokenCache);
        this.restClient = restClient;
        this.uiHomePageUrl = uiHomePageUrl;
        // this.profileService = profileService;

    }

    @Override
    @GetMapping("/login")
    public ResponseEntity<Void> login() {
        URI authUri = buildAuthUri();

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(authUri)
                .build();
    }

    @Override
    @GetMapping("/callback")
    public ResponseEntity<Void> callback(@RequestParam String code, @RequestParam String state) {
        validateState(state);

        URI accessTokenUri = buildAccessTokenUri(code);

        ResponseEntity<GoogleAccessToken> accessTokenResponse = restClient.post()
                .uri(accessTokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(GoogleAccessToken.class);

        HttpStatusCode statusCode = accessTokenResponse.getStatusCode();
        if (!statusCode.is2xxSuccessful()) {
            throw new AuthException("Error getting access token from google. Received code: "
                    + statusCode);
        }

        String accessToken = getString(accessTokenResponse);

        if (accessToken == null || accessToken.isEmpty()) {
            throw new AuthException("Did not receive access token from google");
        }

        String userInfoUri = buildUserInfoUri();

        ResponseEntity<GoogleUser> googleUserResponse = restClient.get()
                .uri(userInfoUri)
                .headers(h -> h.setBearerAuth(accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(GoogleUser.class);

        statusCode = googleUserResponse.getStatusCode();
        if (!statusCode.is2xxSuccessful()) {
            throw new AuthException("Error getting user info from google. Received code: "
                    + statusCode);
        }

        GoogleUser googleUser = googleUserResponse.getBody();
        if (googleUser == null) {
            throw new AuthException("Received empty response for user from google");
        }

        String providerId = String.format("google|%s", googleUser.getSub());
        String name = googleUser.getName();
        String email = googleUser.getEmail();
        String picture = googleUser.getPicture();
        // ObjectId profileId = profileService
        //         .createProfileIfNotExists(providerId, name, email, picture);

        ResponseCookie sessionCookie = generateSessionCookie("stub-id", name, email);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.SET_COOKIE, sessionCookie.toString())
                .location(URI.create(uiHomePageUrl))
                .build();
    }

    private static String getString(ResponseEntity<GoogleAccessToken> response) {
        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode != HttpStatus.OK) {
            throw new AuthException("Could not receive access token from google: Http status code: "
                    + statusCode);
        }

        GoogleAccessToken accessTokenModel = response.getBody();

        if (accessTokenModel == null) {
            throw new AuthException("Did not receive access token from google");
        }

        return accessTokenModel.getAccessToken();
    }

}

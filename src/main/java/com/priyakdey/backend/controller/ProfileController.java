package com.priyakdey.backend.controller;

import com.priyakdey.backend.model.request.ProfileSettingsRequest;
import com.priyakdey.backend.model.response.ProfileDetailsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * @author Priyak Dey
 */
@RestController
@RequestMapping(path = "/api/v1/profile", produces = APPLICATION_JSON_VALUE)
public class ProfileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            ProfileController.class);

    @GetMapping
    public ResponseEntity<ProfileDetailsResponse> getProfile(Principal principal) {
        String profileId = principal.getName();

        ProfileDetailsResponse response = new ProfileDetailsResponse("PRIYAK DEY", "EMAIL@EMAIL.COM", null, null, null, true);


        return ResponseEntity.ok(response);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> getProfile(
            @RequestBody ProfileSettingsRequest profileSettingsRequest) {
        LOGGER.info(profileSettingsRequest.toString());
        return ResponseEntity.created(URI.create("https://localhost:8080/hello")).build();
    }

}

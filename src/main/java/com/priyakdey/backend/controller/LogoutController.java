package com.priyakdey.backend.controller;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

/**
 * @author Priyak Dey
 */
@RestController
@RequestMapping(path = "/api/v1/logout")
public class LogoutController {

    @PostMapping
    public ResponseEntity<Void> logout() {
        ResponseCookie cookie = ResponseCookie.from("session-token", "")
                .path("/")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .maxAge(0)
                .build();


        return ResponseEntity
                .ok()
                .header(SET_COOKIE, cookie.toString())
                .build();
    }

}

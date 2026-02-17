package com.priyakdey.backend.security.config;

import java.time.Instant;

/**
 * @author Priyak Dey
 */
public record StatePayload(String nonce, Instant exp) {

    public boolean isExpired() {
        return Instant.now().isAfter(exp);
    }
}
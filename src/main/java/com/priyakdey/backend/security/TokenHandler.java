package com.priyakdey.backend.security;

import java.time.Instant;
import java.util.List;

/**
 * @author Priyak Dey
 */
public interface TokenHandler<T> {

    String sign(T payload, String issuer, List<String> aud, Instant iat,
                Instant eat);

    T decode(String token, String issuer, List<String> aud, int leeway);

}

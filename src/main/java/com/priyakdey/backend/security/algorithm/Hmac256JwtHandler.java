package com.priyakdey.backend.security.algorithm;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.priyakdey.backend.exception.AuthException;
import com.priyakdey.backend.security.TokenHandler;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Priyak Dey
 */
public class Hmac256JwtHandler<T> implements TokenHandler<T> {

    private final Algorithm algorithm;
    private final Function<T, Map<String, ?>> serializer;
    private final Function<DecodedJWT, T> deserializer;
    private final Map<String, Object> headerClaims;

    public Hmac256JwtHandler(String secret,
                             Function<T, Map<String, ?>> serializer,
                             Function<DecodedJWT, T> deserializer) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.serializer = serializer;
        this.deserializer = deserializer;
        this.headerClaims = Map.of("alg", algorithm.getName(), "typ", "jwt");
    }

    @Override
    public String sign(T payload, String issuer, List<String> aud, Instant iat, Instant eat) {
        return JWT.create()
                .withHeader(headerClaims)
                .withIssuer(issuer)
                .withAudience(aud.toArray(new String[]{}))
                .withIssuedAt(iat)
                .withExpiresAt(eat)
                .withPayload(serializer.apply(payload))
                .sign(algorithm);
    }

    @Override
    public T decode(String token, String issuer, List<String> aud, int leeway) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .withAnyOfAudience(aud.toArray(new String[]{}))
                    .acceptLeeway(leeway)
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return deserializer.apply(decodedJWT);
        } catch (JWTDecodeException e) {
            throw new AuthException(e);
        }
    }
}

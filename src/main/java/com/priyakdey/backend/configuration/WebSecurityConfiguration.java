package com.priyakdey.backend.configuration;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.priyakdey.backend.security.TokenHandler;
import com.priyakdey.backend.security.algorithm.Hmac256JwtHandler;
import com.priyakdey.backend.security.config.SessionPayload;
import com.priyakdey.backend.security.config.SessionTokenProperties;
import com.priyakdey.backend.security.config.StatePayload;
import com.priyakdey.backend.security.config.StateTokenProperties;
import com.priyakdey.backend.security.filter.SessionTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import java.time.Instant;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Priyak Dey
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http, SessionTokenFilter sessionTokenFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers("/api/v1/auth/*/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(sessionTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public TokenHandler<StatePayload> statePayloadTokenHandler(
            StateTokenProperties stateTokenProperties) {
        String secret = stateTokenProperties.getSecret();
        Function<StatePayload, Map<String, ?>> serializer = payload ->
                Map.of("nonce", payload.nonce(), "exp", payload.exp());

        Function<DecodedJWT, StatePayload> deserializer = jwt -> {
            Instant eat = jwt.getExpiresAtAsInstant();
            String nonce = jwt.getClaim("nonce").asString();
            return new StatePayload(nonce, eat);
        };

        return new Hmac256JwtHandler<>(secret, serializer, deserializer);
    }

    @Bean
    public TokenHandler<SessionPayload> sessionPayloadTokenHandler(
            SessionTokenProperties sessionTokenProperties) {
        String secret = sessionTokenProperties.getSecret();
        Function<SessionPayload, Map<String, ?>> serializer = payload ->
                Map.of(
                        "sub", payload.id(),
                        "name", payload.name(),
                        "email", payload.email()
                );


        Function<DecodedJWT, SessionPayload> deserializer = decodedJWT -> {
            String id = decodedJWT.getSubject();
            String name = decodedJWT.getClaim("name").asString();
            String email = decodedJWT.getClaim("email").asString();
            return new SessionPayload(id, name, email);
        };

        return new Hmac256JwtHandler<>(secret, serializer, deserializer);
    }

}

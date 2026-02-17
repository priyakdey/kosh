package com.priyakdey.backend.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Priyak Dey
 */
@ConfigurationProperties(prefix = "app.security.session")
public class SessionTokenProperties {

    private String issuer;
    private String secret;
    private int ttl;
    private int leeway;

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public int getLeeway() {
        return leeway;
    }

    public void setLeeway(int leeway) {
        this.leeway = leeway;
    }
}

package com.priyakdey.backend.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Priyak Dey
 */
@ConfigurationProperties(prefix = "app.security.state")
public class StateTokenProperties {

    private String issuer;
    private List<String> aud;
    private String secret;
    private int ttl;
    private int leeway;

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

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public List<String> getAud() {
        return aud;
    }

    public void setAud(List<String> aud) {
        this.aud = aud;
    }

    public int getLeeway() {
        return leeway;
    }

    public void setLeeway(int leeway) {
        this.leeway = leeway;
    }
}

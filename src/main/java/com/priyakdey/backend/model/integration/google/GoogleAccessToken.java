package com.priyakdey.backend.model.integration.google;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Priyak Dey
 */
public class GoogleAccessToken {

    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "expiresIn")
    private int expiresIn;

    @JsonProperty(value = "scope")
    private String scope;

    @JsonProperty(value = "token_type")
    private String tokenType;

    @JsonProperty(value = "id_token")
    private String idToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}

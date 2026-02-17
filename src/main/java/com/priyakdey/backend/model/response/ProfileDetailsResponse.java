package com.priyakdey.backend.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Priyak Dey
 */
public class ProfileDetailsResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 8962497971405412298L;

    private String name;
    private String email;
    private String avatarUrl;
    private String timezone;
    private String currency;

    @JsonProperty(value = "isFirstTimeLogin")
    private boolean isFirstTimeLogin;

    public ProfileDetailsResponse() {
    }

    public ProfileDetailsResponse(String name, String email, String avatarUrl, String timezone, String currency, boolean isFirstTimeLogin) {
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.timezone = timezone;
        this.currency = currency;
        this.isFirstTimeLogin = isFirstTimeLogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isFirstTimeLogin() {
        return isFirstTimeLogin;
    }

    public void setFirstTimeLogin(boolean firstTimeLogin) {
        isFirstTimeLogin = firstTimeLogin;
    }
}

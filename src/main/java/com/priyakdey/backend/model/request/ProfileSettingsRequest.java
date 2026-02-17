package com.priyakdey.backend.model.request;

import java.io.Serializable;

/**
 * @author Priyak Dey
 */
public class ProfileSettingsRequest implements Serializable {

    private String displayName;
    private String timezone;
    private String currency;

    public ProfileSettingsRequest() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    @Override
    public String toString() {
        return "ProfileSettingsRequest{" +
                "displayName='" + displayName + '\'' +
                ", timezone='" + timezone + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}

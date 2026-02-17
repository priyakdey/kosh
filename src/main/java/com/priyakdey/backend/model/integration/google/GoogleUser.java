package com.priyakdey.backend.model.integration.google;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Priyak Dey
 */
public class GoogleUser {

    @JsonProperty(value = "sub")
    private String sub;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "given_name")
    private String givenName;

    @JsonProperty(value = "family_name")
    private String familyName;

    @JsonProperty(value = "picture")
    private String picture;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "email_verified")
    private boolean emailVerified;

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    @Override
    public String toString() {
        return "GoogleUser{" +
                "sub='" + sub + '\'' +
                ", name='" + name + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", picture='" + picture + '\'' +
                ", email='" + email + '\'' +
                ", emailVerified=" + emailVerified +
                '}';
    }
}

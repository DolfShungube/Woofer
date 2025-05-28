package com.example.woofer;

public class SearchUser{

    private String username;
    private String profileImageUrl;

    public SearchUser(String username, String profileImageUrl) {
        this.username = username;
        this.profileImageUrl = profileImageUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }}
package com.example.woofer;

public class UserStatus {
    private String name;
    private int imageUrl;

    public UserStatus(String name, int imageUrl) {
        this.name = name;
        this.imageUrl= imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getImageUrl() {
        return imageUrl;
    }
}


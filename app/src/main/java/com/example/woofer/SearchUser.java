package com.example.woofer;

public class SearchUser{

    private String username;
    private int ImageResId;
    private int friend_id;

    public SearchUser(String username, int ImageResId, int friend_id) {
        this.username = username;
        this.ImageResId = ImageResId;
        this.friend_id = friend_id;
    }

    public String getUsername() {
        return username;
    }

    public int ImageResId() {
        return  ImageResId;
    }

    public int getFriend_id(){
        return friend_id;
    }

}

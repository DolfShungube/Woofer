package com.example.woofer;

import android.net.Uri;

import java.io.Serializable;

public class Friend implements Serializable{
    private int id;
    private String friendUsername;
    private int ImageResId;

    private boolean isFollowing;

    public Friend(int id, String friendUsername, int ImageResId, boolean isFollowing){
        this.id = id;
        this.friendUsername = friendUsername;
        this.ImageResId = ImageResId;
        this.isFollowing = isFollowing;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFriendUsername(){
        return friendUsername;
    }

    public void setUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }

    public int getImageResId(){
        return ImageResId;
    }
    public void setImageResId(int ImageResId){
        this.ImageResId= ImageResId;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setFollowing(boolean following) {
        isFollowing = following;
    }
}


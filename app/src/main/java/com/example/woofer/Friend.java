package com.example.woofer;

import android.net.Uri;

public class Friend {
    private String username;
    private Uri imageUri;

    public Friend(String username, Uri imageUri){
        this.username = username;
        this.imageUri = imageUri;
    }

    public String getUsername(){
        return username;
    }

    public Uri getImageUri(){
        return imageUri;
    }
}


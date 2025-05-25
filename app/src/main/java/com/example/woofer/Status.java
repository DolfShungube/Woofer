package com.example.woofer;

public class Status {
    private int statusId;
    private String username;
    private String text;
    private String timestamp;
    private int profileImageResId;
    private int upvotes;

    public Status(int statusId, String username, String text, String timestamp, int profileImageResId, int upvotes) {
        this.statusId = statusId;
        this.username = username;
        this.text = text;
        this.timestamp = timestamp;
        this.profileImageResId = profileImageResId;
        this.upvotes = upvotes;
    }
    public int getStatusId(){
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public  String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getProfileImageResId() {
        return profileImageResId;
    }

    public void setProfileImageResId(int profileImageResId) {
        this.profileImageResId = profileImageResId;
    }
}

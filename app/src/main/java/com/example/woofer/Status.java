package com.example.woofer;

public class Status {
    private static String username;
    private static String text;
    private static String timestamp;

    private static int upvotes;

    public Status(String username, String text, String timestamp, int upvotes) {
        this.username = username;
        this.text = text;
        this.timestamp = timestamp;
        this.upvotes = upvotes;
    }

    public Status() {
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
}

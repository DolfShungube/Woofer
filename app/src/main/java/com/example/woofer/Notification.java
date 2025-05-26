package com.example.woofer;

public class Notification {
    private String type;
    private String senderUsername;
    private String timestamp;
    private String message;
    private Integer statusId;
    private int senderId;

    public Notification(String type, String senderUsername, String timestamp, String message, int statusId, int senderId) {
        this.type = type;
        this.senderUsername = senderUsername;
        this.timestamp = timestamp;
        this.message = message;
        this.statusId = statusId;
        this.senderId = senderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getSenderId() {
        return senderId;
    }
}


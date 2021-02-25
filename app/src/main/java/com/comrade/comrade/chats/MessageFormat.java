package com.comrade.comrade.chats;

public class MessageFormat {

    private String Username;
    private String Message;
    private String UniqueId;
    private String senderId;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public MessageFormat(String username, String message, String uniqueId, String senderId) {
        Username = username;
        Message = message;
        UniqueId = uniqueId;
        this.senderId = senderId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getUniqueId() {
        return UniqueId;
    }

    public void setUniqueId(String uniqueId) {
        UniqueId = uniqueId;
    }
}

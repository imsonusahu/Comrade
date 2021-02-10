package com.comrade.comrade.models;

public class UsersListChatModel {


    private String id, userName, lastMsg, timing, profile;

    private boolean status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public UsersListChatModel(String id, String userName, String lastMsg, String timing, String profile, boolean status) {
        this.id = id;
        this.userName = userName;
        this.lastMsg = lastMsg;
        this.timing = timing;
        this.profile = profile;
        this.status = status;
    }
}

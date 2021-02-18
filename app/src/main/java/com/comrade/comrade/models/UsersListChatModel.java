package com.comrade.comrade.models;

public class UsersListChatModel {


    private String myId,uid,roomId,profilePic,matchId,userName;

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UsersListChatModel(String myId, String uid, String roomId, String profilePic, String matchId, String userName) {
        this.myId = myId;
        this.uid = uid;
        this.roomId = roomId;
        this.profilePic = profilePic;
        this.matchId = matchId;
        this.userName = userName;
    }
}

package com.comrade.comrade.models;

public class MatchModel {

    String matchId,matchUserId,liked_by;

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getMatchUserId() {
        return matchUserId;
    }

    public void setMatchUserId(String matchUserId) {
        this.matchUserId = matchUserId;
    }

    public String getLiked_by() {
        return liked_by;
    }

    public void setLiked_by(String liked_by) {
        this.liked_by = liked_by;
    }

    public MatchModel(String matchId, String matchUserId, String liked_by) {
        this.matchId = matchId;
        this.matchUserId = matchUserId;
        this.liked_by = liked_by;
    }
}

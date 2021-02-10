package com.comrade.comrade.models;

public class LikesProfileModel {


    private String id, profilePic,name;

    public LikesProfileModel(String id, String profilePic, String name) {
        this.id = id;
        this.profilePic = profilePic;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

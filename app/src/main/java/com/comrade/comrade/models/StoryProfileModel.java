package com.comrade.comrade.models;

public class StoryProfileModel {


    private String id, profilePic;

    public StoryProfileModel() {
    }

    public StoryProfileModel(String id, String profilePic) {
        this.id = id;
        this.profilePic = profilePic;
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
}

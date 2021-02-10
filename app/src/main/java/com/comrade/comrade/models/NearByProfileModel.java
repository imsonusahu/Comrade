package com.comrade.comrade.models;

public class NearByProfileModel {


    private String id, profilePic, username, age;
    private boolean activeStatus;

    public NearByProfileModel(String id, String profilePic, String username, String age, boolean activeStatus) {
        this.id = id;
        this.profilePic = profilePic;
        this.username = username;
        this.age = age;
        this.activeStatus = activeStatus;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

}

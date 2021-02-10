package com.comrade.comrade.models;

public class UserProfileModel {

    private String name, profilePic, iamHereAns, whatYouLike, whatMakeYouLaugh, kindOfMusicYouListen, location;
    private String school, age;

    public UserProfileModel(String name, String profilePic, String iamHereAns, String whatYouLike, String whatMakeYouLaugh, String kindOfMusicYouListen, String location, String school, String age) {
        this.name = name;
        this.profilePic = profilePic;
        this.iamHereAns = iamHereAns;
        this.whatYouLike = whatYouLike;
        this.whatMakeYouLaugh = whatMakeYouLaugh;
        this.kindOfMusicYouListen = kindOfMusicYouListen;
        this.location = location;
        this.school = school;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getIamHereAns() {
        return iamHereAns;
    }

    public void setIamHereAns(String iamHereAns) {
        this.iamHereAns = iamHereAns;
    }

    public String getWhatYouLike() {
        return whatYouLike;
    }

    public void setWhatYouLike(String whatYouLike) {
        this.whatYouLike = whatYouLike;
    }

    public String getWhatMakeYouLaugh() {
        return whatMakeYouLaugh;
    }

    public void setWhatMakeYouLaugh(String whatMakeYouLaugh) {
        this.whatMakeYouLaugh = whatMakeYouLaugh;
    }

    public String getKindOfMusicYouListen() {
        return kindOfMusicYouListen;
    }

    public void setKindOfMusicYouListen(String kindOfMusicYouListen) {
        this.kindOfMusicYouListen = kindOfMusicYouListen;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


}

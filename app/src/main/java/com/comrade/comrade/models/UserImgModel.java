package com.comrade.comrade.models;

public class UserImgModel {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    String imgUrl,uid,name,age,location;

    public UserImgModel(String name, String age, String location) {
        this.name = name;
        this.age = age;
        this.location = location;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public UserImgModel(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public UserImgModel(String imgUrl, String uid) {
        this.imgUrl = imgUrl;
        this.uid = uid;
    }
}

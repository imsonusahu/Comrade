package com.comrade.comrade.models;

public class ItemModel {
    private String images;
    private String name, job, location,school,distance,age;

    private boolean isVerified;

    public ItemModel(String image, String age, String name, String job, String location, String school, String distance, boolean isVerified) {
        this.images = image;
        this.age = age;
        this.name = name;
        this.job = job;
        this.location = location;
        this.school = school;
        this.distance = distance;
        this.isVerified = isVerified;
    }



    public String getImage() {
        return images;
    }

    public void setImage(String  image) {
        this.images = image;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public ItemModel() {
    }


}

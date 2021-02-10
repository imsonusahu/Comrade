package com.comrade.comrade.models;

public class WhoLikedModel {

    private String _id,phone,about,company, dob,drinking, education,email;
    private String gender,height,interest_gender,interest_in,job,relationship_status;
    private String sexuality,smoking,username,moods,profilePic,distance,lat,log;

    public WhoLikedModel(String _id, String phone, String about, String company, String dob, String drinking, String education, String email, String gender, String height, String interest_gender, String interest_in, String job, String relationship_status, String sexuality, String smoking, String username, String moods, String profilePic, String distance, String lat, String log) {
        this._id = _id;
        this.phone = phone;
        this.about = about;
        this.company = company;
        this.dob = dob;
        this.drinking = drinking;
        this.education = education;
        this.email = email;
        this.gender = gender;
        this.height = height;
        this.interest_gender = interest_gender;
        this.interest_in = interest_in;
        this.job = job;
        this.relationship_status = relationship_status;
        this.sexuality = sexuality;
        this.smoking = smoking;
        this.username = username;
        this.moods = moods;
        this.profilePic = profilePic;
        this.distance = distance;
        this.lat = lat;
        this.log = log;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDrinking() {
        return drinking;
    }

    public void setDrinking(String drinking) {
        this.drinking = drinking;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getInterest_gender() {
        return interest_gender;
    }

    public void setInterest_gender(String interest_gender) {
        this.interest_gender = interest_gender;
    }

    public String getInterest_in() {
        return interest_in;
    }

    public void setInterest_in(String interest_in) {
        this.interest_in = interest_in;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getRelationship_status() {
        return relationship_status;
    }

    public void setRelationship_status(String relationship_status) {
        this.relationship_status = relationship_status;
    }

    public String getSexuality() {
        return sexuality;
    }

    public void setSexuality(String sexuality) {
        this.sexuality = sexuality;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMoods() {
        return moods;
    }

    public void setMoods(String moods) {
        this.moods = moods;
    }


}

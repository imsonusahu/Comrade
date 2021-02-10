package com.comrade.comrade.models;

public class HashTagModel {


    private String hashtags;

    private boolean isSelected = false;

    public HashTagModel(String hashtags, boolean isSelected) {
        this.hashtags = hashtags;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public HashTagModel() {
    }

    public HashTagModel(String hashtags) {
        this.hashtags = hashtags;
    }

    public String getHashtags() {
        return hashtags;
    }

    public void setHashtags(String hashtags) {
        this.hashtags = hashtags;
    }
}

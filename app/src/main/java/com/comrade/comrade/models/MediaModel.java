package com.comrade.comrade.models;

import android.net.Uri;

public class MediaModel {

    Uri imageUri;


    public MediaModel(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public MediaModel() {
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}

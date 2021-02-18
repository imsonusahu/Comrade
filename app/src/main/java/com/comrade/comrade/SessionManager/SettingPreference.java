package com.comrade.comrade.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SettingPreference {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public Context context;
    int PRIVATE_MODE = 0;


    public final String uid="uid";
    public final String phone = "phone";
    public final String name = "name";
    public final String age = "age";
    public final String gender = "gender";
    public final String lookingFor = "lookingFor";
    public final String sexuality = "sexuality";
    public final String relStatus = "relStatus";
    public final String height = "height";
    public final String shareMood = "shareMood";
    public final String aboutUs = "aboutUs";
    public final String doYouDrink = "doYouDrink";
    public final String doYouSmoke = "doYouSmoke";
    public final String goToSchool = "goToSchool";
    public final String jobTitle = "jobTitle";
    public final String company = "company";

    public final String latitude = "latitude";
    public final String longitude = "longitude";
    public final String userType = "userType";
    public final String email = "email";

    //hashTags

    public final String hasTag1="hasTag1";
    public final String hasTag2="hasTag2";

    public final String hasTag3="hasTag3";

    public final String hasTag4="hasTag4";

    public final String hasTag5="hasTag5";


    public SettingPreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("LOGIN", PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }



    public void setEmail(String email){

        editor.putString("email",email);
        editor.apply();
    }


    public void setUserType(String userType){

        editor.putString("userType",userType);
        editor.apply();
    }


    public void setLatLong(double latitude,double longitude){

        editor.putString("latitude", String.valueOf(latitude));
        editor.putString("longitude", String.valueOf(longitude));

        editor.apply();
    }






    public void setUid(String uid){

        editor.putString("uid",uid);
        editor.apply();
    }

    public void setName(String name, String age) {
        editor.putString("name", name);
        editor.putString("age", age);
        editor.apply();
    }

    public void setGender(String gender) {
        editor.putString("gender", gender);
        editor.apply();
    }

    public void setPhone(String phone) {
        editor.putString("phone", phone);
        editor.apply();
    }

    public void lookingForGender(String lookingFor) {
        editor.putString("lookingFor", lookingFor);
        editor.apply();
    }

    public void sexualOrientation(String sexuality) {

        editor.putString("sexuality", sexuality);
        editor.apply();

    }

    public void relStatus(String relStatus) {

        editor.putString("relStatus", relStatus);
        editor.apply();
    }

    public void setHeight(String height) {

        editor.putString("height", height);
        editor.apply();

    }

    public void setWhatMakeHappy(String hasTag1,String hasTag2,String hasTag3,String hasTag4,String hasTag5) {

        editor.putString("hasTag1", hasTag1);
        editor.putString("hasTag2", hasTag2);
        editor.putString("hasTag3", hasTag3);
        editor.putString("hasTag4", hasTag4);
        editor.putString("hasTag5", hasTag5);
        editor.apply();
    }

    public void setShareMood(String shareMood) {

        editor.putString("shareMood", shareMood);
        editor.apply();

    }

    public void doYouDrink(String doYouDrink) {

        editor.putString("doYouDrink", doYouDrink);
        editor.apply();

    }

    public void doYouSmoke(String doYouSmoke) {

        editor.putString("doYouSmoke", doYouSmoke);
        editor.apply();

    }

    public void setAbout(String aboutUs) {

        editor.putString("aboutUs", aboutUs);
        editor.apply();
    }

    public void gotoSchool(String goToSchool) {

        editor.putString("goToSchool", goToSchool);
        editor.apply();
    }

    public void doYouWork(String jobTitle, String company) {

        editor.putString("jobTitle", jobTitle);
        editor.putString("company", company);
        editor.apply();
    }


    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();

        user.put(phone, sharedPreferences.getString(phone, null));
        user.put(name, sharedPreferences.getString(name, null));
        user.put(age, sharedPreferences.getString(age, null));
        user.put(gender, sharedPreferences.getString(gender, null));
        user.put(lookingFor, sharedPreferences.getString(lookingFor, null));
        user.put(sexuality, sharedPreferences.getString(sexuality, null));
        user.put(relStatus, sharedPreferences.getString(relStatus, null));

        user.put(height, sharedPreferences.getString(height, null));
        user.put(shareMood, sharedPreferences.getString(shareMood, null));
        user.put(aboutUs, sharedPreferences.getString(aboutUs, null));

        user.put(doYouDrink, sharedPreferences.getString(doYouDrink, null));
        user.put(doYouSmoke, sharedPreferences.getString(doYouSmoke, null));
        user.put(goToSchool, sharedPreferences.getString(goToSchool, null));
        user.put(jobTitle, sharedPreferences.getString(jobTitle, null));
        user.put(company, sharedPreferences.getString(company, null));
        user.put(uid, sharedPreferences.getString(uid, null));

        user.put(longitude, sharedPreferences.getString(longitude, null));
        user.put(latitude, sharedPreferences.getString(latitude, null));

        user.put(hasTag1, sharedPreferences.getString(hasTag1, null));
        user.put(hasTag2, sharedPreferences.getString(hasTag2, null));
        user.put(hasTag3, sharedPreferences.getString(hasTag3, null));
        user.put(hasTag4, sharedPreferences.getString(hasTag4, null));
        user.put(hasTag5, sharedPreferences.getString(hasTag5, null));


        String LOGIN = "IS_LOGIN";
        editor.putBoolean(LOGIN, true);

        return user;

    }

    public void clearPreference() {

        editor.clear();
        editor.commit();

    }
}


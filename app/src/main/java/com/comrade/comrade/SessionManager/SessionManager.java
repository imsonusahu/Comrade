package com.comrade.comrade.SessionManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.comrade.comrade.activity.MainActivity;
import com.comrade.comrade.activity.SignUpChooserActivity;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public Context context;
    int PRIVATE_MODE=0;
    private final String PREF_NAME="LOGIN";

    private final String LOGIN="IS_LOGIN";
    public final String uid="uid";


    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences("LOGIN",PRIVATE_MODE);
        editor=sharedPreferences.edit();
    }

    public void createSession(String uid){

        editor.putBoolean(LOGIN,true);
        editor.putString("uid",uid);


        editor.apply();
    }
    public boolean isLogin(){

        return sharedPreferences.getBoolean(LOGIN,false);

    }
    public void checkingLogin(){
        if (this.isLogin()){

            Intent i=new Intent(context, MainActivity.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }
    }
    public HashMap<String,String> getUserDetail(){

        HashMap<String ,String > user=new HashMap<>();

        user.put(uid,sharedPreferences.getString(uid,null));

        editor.putBoolean(LOGIN,true);

        return user;

    }
    public void logout(){

        editor.clear();
        editor.commit();
        Intent i=new Intent(context, SignUpChooserActivity.class);
        context.startActivity(i);
        ((SignUpChooserActivity)context).finish();
    }
}

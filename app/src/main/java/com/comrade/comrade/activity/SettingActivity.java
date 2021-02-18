package com.comrade.comrade.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.SessionManager.SessionManager;
import com.comrade.comrade.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {


    SessionManager sessionManager;
    QueryPreferences queryPreferences;

    ActivitySettingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_setting);

        sessionManager=new SessionManager(this);
        queryPreferences=new QueryPreferences(this);

        onClick();
    }

    private void onClick() {


        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sessionManager.logout();
                queryPreferences.clearPreference();


            }
        });
    }
}
package com.comrade.comrade.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.SessionManager;
import com.comrade.comrade.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {


    ActivitySplashBinding binding;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        requestWindowFeature(1);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        binding.logoName.setAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fade_in));

        sessionManager = new SessionManager(this);

        new Handler().postDelayed(() -> {


            if (sessionManager.isLogin()){

                Intent intent=new Intent(SplashActivity.this,LocationActivity.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent=new Intent(SplashActivity.this,LocationActivity.class);
                startActivity(intent);
                finish();
            }


        }, 500);
    }
}
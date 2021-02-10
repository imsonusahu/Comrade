package com.comrade.comrade.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {


    private ActivityChatBinding binding;
    private QueryPreferences queryPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        queryPreferences = new QueryPreferences(this);

    }
}
package com.comrade.comrade.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import com.comrade.comrade.R;
import com.comrade.comrade.adapters.ProfileAdapter;
import com.comrade.comrade.databinding.ActivityUserProfileBinding;
import com.comrade.comrade.models.UserProfileModel;

import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity {


    private final ArrayList<UserProfileModel> arrayList = new ArrayList<>();
    private ProfileAdapter profileAdapter;
    private ActivityUserProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile);


        viewInit();

        arrayList.add(new UserProfileModel("Josef langford",
                "https://i.pinimg.com/originals/7d/21/f4/7d21f47169e4276a368266329abf8c79.jpg",
                "To date new people.",
                "I like listening to music ",
                "Stand-up comedy Movie, jocks",
                "Depends on mood!",
                "Indore, MP", "Aks University Satna", "24"));

        arrayList.add(new UserProfileModel("John langford",
                "https://i.pinimg.com/originals/7d/21/f4/7d21f47169e4276a368266329abf8c79.jpg",
                "To date new people.",
                "I like listening to music ",
                "Stand-up comedy Movie, jocks",
                "Depends on mood!",
                "Indore, MP", "Kalinga University", "22"));

        arrayList.add(new UserProfileModel("Katherine langford",
                "https://c4.wallpaperflare.com/wallpaper/554/951/606/katherine-langford-hd-wallpaper-preview.jpg",
                "To date new people.",
                "I like listening to music ",
                "Stand-up comedy Movie, jocks",
                "Depends on mood!",
                "Indore, MP", "Oxoford University london", "23"));

        arrayList.add(new UserProfileModel("Katherine langford",
                "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwall.alphacoders.com%2Ftags.php%3Ftid%3D67683&psig=AOvVaw3-XKgKuUXansHsLTc-Eb4M&ust=1610460375874000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCPjI2siGlO4CFQAAAAAdAAAAABAD",
                "To date new people.",
                "I like listening to music ",
                "Stand-up comedy Movie, jocks",
                "Depends on mood!",
                "Indore, MP", "Sage University Satna", "26"));


    }

    private void viewInit() {

        binding.rvProfile.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        binding.rvProfile.setHasFixedSize(true);
        binding.rvProfile.setItemViewCacheSize(20);
        binding.rvProfile.setDrawingCacheEnabled(true);
        SnapHelper startSnapHelper = new PagerSnapHelper();
        startSnapHelper.attachToRecyclerView(binding.rvProfile);
        profileAdapter = new ProfileAdapter(this, arrayList);

        binding.rvProfile.setAdapter(profileAdapter);


    }
}
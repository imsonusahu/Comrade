package com.comrade.comrade.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.comrade.comrade.R;
import com.comrade.comrade.adapters.pagerAdapter.MenuTabAdapter;
import com.comrade.comrade.databinding.ActivityMenuBinding;
import com.comrade.comrade.fragments.menuTabs.ProfileVisitorsListFragment;
import com.comrade.comrade.fragments.menuTabs.UsersProfilesListFragment;
import com.comrade.comrade.fragments.menuTabs.WhoLikesFragment;

public class MenuActivity extends AppCompatActivity {


    ActivityMenuBinding binding;

    MenuTabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);


        try {

            setupViewPager(binding.viewPagerTwo);
            binding.tabsLayout.setupWithViewPager(binding.viewPagerTwo);

        } catch (Exception e) {
            e.printStackTrace();

        }

        binding.backBtn.setOnClickListener(v -> onBackPressed());

    }

    private void setupViewPager(ViewPager viewPager) {

        if (viewPager != null) {

            WhoLikesFragment whoLikesFragment = new WhoLikesFragment();
            ProfileVisitorsListFragment profileVisitorsListFragment = new ProfileVisitorsListFragment();
            UsersProfilesListFragment usersProfilesListFragment = new UsersProfilesListFragment();

            adapter = new MenuTabAdapter(this.getSupportFragmentManager());
            adapter.addFragment(usersProfilesListFragment, "Profiles");
            adapter.addFragment(whoLikesFragment, "Who liked");
            adapter.addFragment(profileVisitorsListFragment, "Visitors");


        }

        if (viewPager != null) {
            viewPager.setOffscreenPageLimit(4);
            viewPager.setAdapter(adapter);
        }
    }
}
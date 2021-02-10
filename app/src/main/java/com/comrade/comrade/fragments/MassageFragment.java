package com.comrade.comrade.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.comrade.comrade.R;
import com.comrade.comrade.adapters.pagerAdapter.TabAdapter;
import com.comrade.comrade.databinding.FragmentMassegeBinding;

public class MassageFragment extends Fragment {


    FragmentMassegeBinding binding;
    private TabAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_massege, container, false);


        try {

            setupViewPager(binding.viewPager);
            binding.tabLayout.setupWithViewPager(binding.viewPager);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return binding.getRoot();
    }

    private void setupViewPager(ViewPager viewPager) {


        if (viewPager != null) {

            UserChatListFrag userChatListFrag = new UserChatListFrag();
           // MatchesFragment matchesFragment = new MatchesFragment();


            pagerAdapter = new TabAdapter(getChildFragmentManager());
            pagerAdapter.addFragment(userChatListFrag, "Messages");
            //pagerAdapter.addFragment(matchesFragment, "Feed");


        }if (viewPager != null) {
            viewPager.setOffscreenPageLimit(4);
            viewPager.setAdapter(pagerAdapter);
        }
    }
}
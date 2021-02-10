package com.comrade.comrade.fragments.query;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.databinding.FragmentHeightBinding;


public class HeightFragment extends Fragment {


    FragmentHeightBinding binding;
    View view;
    private QueryPreferences queryPreferences;
    String height;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_height, container, false);

        TextView textView = getActivity().findViewById(R.id.titleBase);
        textView.setText("Height");

        queryPreferences = new QueryPreferences(getActivity());

        onClick();


        return binding.getRoot();
    }

    private void onClick() {


        Button nextButton = getActivity().findViewById(R.id.myBtnNext);
        nextButton.setOnClickListener(v -> {

            Fragment fragment = new WhatMakeHappyFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_right,
                    R.anim.slide_in_left,
                    R.anim.slide_out_left);

            fragmentTransaction.replace(R.id.container,
                    fragment).addToBackStack("")
                    .commitAllowingStateLoss();
        });


        binding.heightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                height = seekBar.getProgress() + "";
                binding.heightText.setText(height);

                Log.e("height", height);
                putData(height);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void putData(String height) {
        queryPreferences.setHeight(height);
    }
}
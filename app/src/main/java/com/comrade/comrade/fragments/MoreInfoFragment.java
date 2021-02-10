package com.comrade.comrade.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.comrade.comrade.R;
import com.comrade.comrade.databinding.FragmentMoreInfoBinding;


public class MoreInfoFragment extends Fragment {


    FragmentMoreInfoBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more_info, container, false);

        onClick();

        return binding.getRoot();
    }

    private void onClick() {


        binding.height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Height", Toast.LENGTH_SHORT).show();
            }
        });

        binding.cildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Children", Toast.LENGTH_SHORT).show();
            }
        });
        binding.drinking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Drinking Sometimes", Toast.LENGTH_SHORT).show();
            }
        });
        binding.languageSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Hindi, English", Toast.LENGTH_SHORT).show();
            }
        });
        binding.relationStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Relationship status", Toast.LENGTH_SHORT).show();
            }
        });
        binding.sexuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Sexuality", Toast.LENGTH_SHORT).show();
            }
        });
        binding.smoking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Smoking", Toast.LENGTH_SHORT).show();
            }
        });
        binding.myOpinions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "My opinions", Toast.LENGTH_SHORT).show();
            }
        });
        binding.interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Add your interest", Toast.LENGTH_SHORT).show();
            }
        });
        binding.verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Verify your profile", Toast.LENGTH_SHORT).show();
            }
        });

        binding.addNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Add Nmber", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
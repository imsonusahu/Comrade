package com.comrade.comrade.fragments.query;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import com.comrade.comrade.R;
import com.comrade.comrade.adapters.HashtagAdapter;
import com.comrade.comrade.databinding.FragmentWhatMakeHappyBinding;
import com.comrade.comrade.models.HashTagModel;

import java.util.ArrayList;


public class WhatMakeHappyFragment extends Fragment {


    ArrayList<HashTagModel> arrayList = new ArrayList<>();


    FragmentWhatMakeHappyBinding binding;
    HashtagAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_what_make_happy, container, false);


        TextView textView = ((AppCompatActivity) getActivity()).findViewById(R.id.titleBase);
        textView.setText("Happiness");


        onClick();

        arrayList.clear();

        arrayList.add(new HashTagModel("Music"));
        arrayList.add(new HashTagModel("Tea"));
        arrayList.add(new HashTagModel("Videos"));
        arrayList.add(new HashTagModel("Coffee"));
        arrayList.add(new HashTagModel("Music"));
        arrayList.add(new HashTagModel("Guitar"));
        arrayList.add(new HashTagModel("Mountain"));
        arrayList.add(new HashTagModel("Dance"));
        arrayList.add(new HashTagModel("Trivia"));
        arrayList.add(new HashTagModel("Travel"));
        arrayList.add(new HashTagModel("Food"));
        arrayList.add(new HashTagModel("Movie"));
        arrayList.add(new HashTagModel("Piano"));


        binding.makeHappyRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new HashtagAdapter(getActivity(), arrayList);
        binding.makeHappyRv.setAdapter(adapter);

        return binding.getRoot();
    }

    private void onClick() {


        Button nextButton = getActivity().findViewById(R.id.myBtnNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new MoodFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_left,
                        R.anim.slide_out_left);

                fragmentTransaction.replace(R.id.container, fragment).addToBackStack("").commitAllowingStateLoss();
                // startActivity(new Intent(getActivity(), ProfileActivity.class));

            }
        });

    }
}
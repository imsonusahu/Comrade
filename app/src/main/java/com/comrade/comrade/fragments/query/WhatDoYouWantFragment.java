package com.comrade.comrade.fragments.query;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.comrade.comrade.R;


public class WhatDoYouWantFragment extends Fragment {


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_what_do_you_want, container, false);

        TextView textView=getView().findViewById(R.id.titleBase);
        textView.setText("What you want?");

        Button nextButton = getActivity().findViewById(R.id.myBtnNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new TellUsAboutYouFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_left,
                        R.anim.slide_out_left);

                fragmentTransaction.replace(R.id.container, fragment).addToBackStack("").commitAllowingStateLoss();
                // startActivity(new Intent(getActivity(), ProfileActivity.class));

            }
        });


        return view;
    }
}
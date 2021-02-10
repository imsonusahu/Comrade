package com.comrade.comrade.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.comrade.comrade.R;
import com.comrade.comrade.activity.MainActivity;
import com.comrade.comrade.databinding.FragmentCompleteOrSkipFragBinding;
import com.comrade.comrade.fragments.query.SexualityFragment;

public class CompleteOrSkipFragment extends Fragment {


    FragmentCompleteOrSkipFragBinding binding;


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_complete_or_skip_frag, container, false);


        onClick();

        view = binding.getRoot();

        return view;

    }

    private void onClick() {

        binding.maybeLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        Button nextButton = getActivity().findViewById(R.id.myBtnNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new SexualityFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_left);

                fragmentTransaction.replace(R.id.container, fragment).addToBackStack("").commit();

                Toast.makeText(getActivity(), "Continue", Toast.LENGTH_SHORT).show();

                //startActivity(new Intent(getActivity(), ProfileActivity.class));

            }
        });
    }
}
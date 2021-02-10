package com.comrade.comrade.fragments.query;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.databinding.FragmentTellUsAboutYouBinding;


public class TellUsAboutYouFragment extends Fragment {


    FragmentTellUsAboutYouBinding binding;

    QueryPreferences queryPreferences;

    String about;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tell_us_about_you, container, false);


        queryPreferences = new QueryPreferences(getActivity());

        TextView textView = getActivity().findViewById(R.id.titleBase);
        textView.setText("About");

        onClick();


        return binding.getRoot();
    }

    private void onClick() {


        Button nextButton = getActivity().findViewById(R.id.myBtnNext);
        nextButton.setOnClickListener(v -> {


            about = binding.tellUsAboutYou.getText().toString();


            Fragment fragment = new DrinkFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_left,
                    R.anim.slide_out_left);


            setData(about);

            Log.d("",about+"");


            fragmentTransaction.replace(R.id.container, fragment).addToBackStack("").commitAllowingStateLoss();
            // startActivity(new Intent(getActivity(), ProfileActivity.class));

        });
    }

    private void setData(String about) {


        queryPreferences.setAbout(about);

    }
}
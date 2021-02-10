package com.comrade.comrade.fragments.query;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.databinding.FragmentSmokeBinding;


public class SmokeFragment extends Fragment {


    FragmentSmokeBinding binding;
    private String smoke;

    QueryPreferences queryPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_smoke, container, false);

        queryPreferences=new QueryPreferences(getActivity());
        TextView textView= getActivity().findViewById(R.id.titleBase);
        textView.setText("Smoke");



        onClick();



        return binding.getRoot();
    }

    private void onClick() {


        RadioGroup rg = binding.getRoot().findViewById(R.id.radioGroupSmoke);

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.yesSmoke:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Yes", Toast.LENGTH_SHORT).show();
                    smoke = "Yes";
                    break;

                case R.id.noSmoke:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "No", Toast.LENGTH_SHORT).show();
                    smoke = "No";
                    break;

                case R.id.sometimesSmoke:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Sometimes", Toast.LENGTH_SHORT).show();
                    smoke = "Sometimes";
                    break;

                case R.id.notSaySmoke:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "I'd rather than say", Toast.LENGTH_SHORT).show();
                    smoke = "I'd rather than say";
                    break;

            }
        });



        Button nextButton = getActivity().findViewById(R.id.myBtnNext);
        nextButton.setOnClickListener(v -> {

            setData(smoke);

            Fragment fragment = new SchoolFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_left,
                    R.anim.slide_out_left);

            fragmentTransaction.replace(R.id.container, fragment).addToBackStack("").commitAllowingStateLoss();
            // startActivity(new Intent(getActivity(), ProfileActivity.class));

        });




    }

    private void setData(String smoke) {
        queryPreferences.doYouSmoke(smoke);
    }
}
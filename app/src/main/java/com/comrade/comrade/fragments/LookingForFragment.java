package com.comrade.comrade.fragments;

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
import com.comrade.comrade.databinding.FragmentLookingForBinding;

public class LookingForFragment extends Fragment {


    FragmentLookingForBinding binding;
    View view;
    String lookingFor;

    QueryPreferences queryPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_looking_for, container, false);

        TextView nameText = getActivity().findViewById(R.id.titleBase);
        nameText.setText("Interested in");

        queryPreferences=new QueryPreferences(getActivity());

        onClick();

        view = binding.getRoot();

        return view;
    }

    private void onClick() {

        RadioGroup rg = binding.getRoot().findViewById(R.id.radioGroupLookingFor);

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.lookingForMale:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "lookingForMale", Toast.LENGTH_SHORT).show();


                    lookingFor="Male";

                    break;
                case R.id.lookingForFemale:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "lookingForFemale", Toast.LENGTH_SHORT).show();

                    lookingFor="Female";
                    break;
                case R.id.lookingForBisexualMale:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "lookingForBisexualMale", Toast.LENGTH_SHORT).show();
                    lookingFor="Bisexual  Male";

                    break;
                case R.id.lookingForBisexualFemale:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "lookingForBisexualFemale", Toast.LENGTH_SHORT).show();

                    lookingFor="Bisexual Female";

                    break;
            }
        });



        Button nextButton = getActivity().findViewById(R.id.myBtnNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                putData(lookingFor);
                Fragment fragment = new CompleteOrSkipFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_left);

                fragmentTransaction.replace(R.id.container, fragment).addToBackStack("").commit();

                //startActivity(new Intent(getActivity(), ProfileActivity.class));

            }
        });
    }

    private void putData(String lookingFor) {

        queryPreferences.lookingForGender(lookingFor);
    }
}
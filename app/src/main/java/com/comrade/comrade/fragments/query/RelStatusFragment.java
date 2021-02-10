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
import com.comrade.comrade.databinding.FragmentRelStatusBinding;


public class RelStatusFragment extends Fragment {


    FragmentRelStatusBinding binding;
    private String relStatus;

    QueryPreferences queryPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_rel_status, container, false);
        TextView textView= getActivity().findViewById(R.id.titleBase);
        textView.setText("Relationship status");

        queryPreferences=new QueryPreferences(getActivity());

        onClick();


        return binding.getRoot();
    }

    private void onClick() {


        RadioGroup rg = binding.getRoot().findViewById(R.id.relStatusRadioGroup);

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.single:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Single", Toast.LENGTH_SHORT).show();
                    relStatus = "Single";
                    break;

                case R.id.taken:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Taken", Toast.LENGTH_SHORT).show();
                    relStatus = "Taken";
                    break;

                case R.id.in_a_relationship:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "In a relationship", Toast.LENGTH_SHORT).show();
                    relStatus = "Open";
                    break;

                case R.id.open:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Open", Toast.LENGTH_SHORT).show();
                    relStatus = "Open";
                    break;
            }
        });




        Button nextButton = getActivity().findViewById(R.id.myBtnNext);
        nextButton.setOnClickListener(v -> {

            Fragment fragment = new HeightFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_left,
                    R.anim.slide_out_left);

            fragmentTransaction.replace(R.id.container, fragment).addToBackStack("").commitAllowingStateLoss();
            // startActivity(new Intent(getActivity(), ProfileActivity.class));
            putData(relStatus);

        });




    }

    private void putData(String relStatus) {

        queryPreferences.relStatus(relStatus);
    }
}
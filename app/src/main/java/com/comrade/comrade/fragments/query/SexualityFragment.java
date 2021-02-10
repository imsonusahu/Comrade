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
import com.comrade.comrade.databinding.FragmentSexualityBinding;


public class SexualityFragment extends Fragment {


    FragmentSexualityBinding binding;
    private String sexuality;

    QueryPreferences queryPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sexuality, container, false);

        TextView textView = getActivity().findViewById(R.id.titleBase);
        textView.setText("Sexuality");

        queryPreferences=new QueryPreferences(getActivity());

        onClick();

        return binding.getRoot();
    }

    private void onClick() {

        RadioGroup rg = binding.getRoot().findViewById(R.id.sexualityRadioGroup);

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.straight:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Straight", Toast.LENGTH_SHORT).show();
                    sexuality = "Straight";
                    break;

                case R.id.gay:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Gay", Toast.LENGTH_SHORT).show();
                    sexuality = "Gay";
                    break;

                case R.id.lesbian:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Lesbian", Toast.LENGTH_SHORT).show();
                    sexuality = "Lesbian";
                    break;

                case R.id.bisexual:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Bisexual", Toast.LENGTH_SHORT).show();
                    sexuality = "Bisexual";
                    break;

                case R.id.asexual:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Asexual", Toast.LENGTH_SHORT).show();
                    sexuality = "Asexual";
                    break;

                case R.id.demisexual:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Demiexual", Toast.LENGTH_SHORT).show();
                    sexuality = "Demiexual";
                    break;

                case R.id.pansexual:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Pansexual", Toast.LENGTH_SHORT).show();
                    sexuality = "Pansexual";
                    break;

                case R.id.queer:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Queer", Toast.LENGTH_SHORT).show();
                    sexuality = "Queer";

                    break;

                case R.id.questioning:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Questioning", Toast.LENGTH_SHORT).show();
                    sexuality = "Questioning";
                    break;

            }
        });


        Button nextButton = getActivity().findViewById(R.id.myBtnNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                putData(sexuality);

                Fragment fragment = new RelStatusFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_left,
                        R.anim.slide_out_left);
                fragmentTransaction.replace(R.id.container, fragment).addToBackStack("").commitAllowingStateLoss();
                // startActivity(new Intent(getActivity(), ProfileActivity.class));


            }
        });

    }

    private void putData(String sexuality) {
        queryPreferences.sexualOrientation(sexuality);
    }
}
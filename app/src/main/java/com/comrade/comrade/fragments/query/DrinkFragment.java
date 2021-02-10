package com.comrade.comrade.fragments.query;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.databinding.FragmentDrinkBinding;


public class DrinkFragment extends Fragment {


    FragmentDrinkBinding binding;
    QueryPreferences queryPreferences;
    String drink;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_drink, container, false);

        queryPreferences=new QueryPreferences(getActivity());

        TextView textView= getActivity().findViewById(R.id.titleBase);
        textView.setText("Drink");


        onClick();


        return binding.getRoot();
    }

    private void onClick() {


        RadioGroup rg = binding.getRoot().findViewById(R.id.radioGroupDrink);

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.yesDrink:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Yes", Toast.LENGTH_SHORT).show();
                    drink = "Yes";
                    break;

                case R.id.noDrink:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "No", Toast.LENGTH_SHORT).show();
                    drink = "No";
                    break;

                case R.id.sometimesDrink:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Sometimes", Toast.LENGTH_SHORT).show();
                    drink = "Sometimes";
                    break;

                case R.id.notSayDrink:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "I'd rather than say", Toast.LENGTH_SHORT).show();
                    drink = "I'd rather than say";
                    break;

            }
        });



        Button nextButton = getActivity().findViewById(R.id.myBtnNext);
        nextButton.setOnClickListener(v -> {


            setData(drink);
            Fragment fragment = new SmokeFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_left,
                    R.anim.slide_out_left);

            fragmentTransaction.replace(R.id.container, fragment).addToBackStack("").commitAllowingStateLoss();
            // startActivity(new Intent(getActivity(), ProfileActivity.class));

        });
    }

    private void setData(String drink) {


        queryPreferences.doYouDrink(drink);
    }
}
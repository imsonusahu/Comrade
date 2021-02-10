package com.comrade.comrade.fragments.query;

import android.graphics.Color;
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
import com.comrade.comrade.databinding.FragmentGenderBinding;
import com.comrade.comrade.fragments.LookingForFragment;


public class GenderFragment extends Fragment {


    FragmentGenderBinding binding;

    View view;
    private QueryPreferences queryPreferences;

    String gender;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gender, container, false);

        TextView nameText = getActivity().findViewById(R.id.titleBase);
        nameText.setText("Gender");

        queryPreferences=new QueryPreferences(getActivity());

        onClick();

        view = binding.getRoot();


        return view;


    }

    private void onClick() {


        RadioGroup rg = binding.getRoot().findViewById(R.id.radioGroup);

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.male:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Male", Toast.LENGTH_SHORT).show();
                    binding.male.setBackgroundResource(R.drawable.male);
                    binding.male.setAlpha(1);
                    binding.female.setAlpha((float) 0.5);


                    gender="Male";


                    binding.female.setBackgroundResource(R.drawable.girl);
                    binding.male.setTextColor(Color.parseColor("#EC407A"));
                    binding.female.setTextColor(Color.parseColor("#000000"));

                    break;
                case R.id.female:
                    // do operations specific to this selection

                    Toast.makeText(getContext(), "Female", Toast.LENGTH_SHORT).show();
                    binding.male.setBackgroundResource(R.drawable.boy);
                    binding.female.setBackgroundResource(R.drawable.female);
                    binding.female.setAlpha(1);
                    binding.male.setAlpha((float) 0.5);

                    gender="Female";


                    putData(binding.female.getText().toString());
                    binding.male.setTextColor(Color.parseColor("#000000"));
                    binding.female.setTextColor(Color.parseColor("#EC407A"));

                    break;
            }
        });


        Button nextButton = getActivity().findViewById(R.id.myBtnNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                putData(gender);

                Fragment fragment = new LookingForFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_left,
                        R.anim.slide_out_left);

                fragmentTransaction.replace(R.id.container, fragment).addToBackStack("").commitAllowingStateLoss();
                // startActivity(new Intent(getActivity(), ProfileActivity.class));

            }
        });
    }

    public void putData(String gender) {

        queryPreferences.setGender(gender);


    }
}
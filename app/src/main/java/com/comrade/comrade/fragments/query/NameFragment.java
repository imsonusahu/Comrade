package com.comrade.comrade.fragments.query;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.databinding.FragmentNameBinding;


public class NameFragment extends Fragment {


    FragmentNameBinding binding;
    View view;


    String name, age;
    QueryPreferences queryPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_name, container, false);

        TextView nameText = getActivity().findViewById(R.id.titleBase);
        nameText.setText("Name & age");
        queryPreferences = new QueryPreferences(getActivity());


        onClick();

        view = binding.getRoot();


        return view;
    }


    private void onClick() {


        Button nextButton = getActivity().findViewById(R.id.myBtnNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = binding.fullName.getText().toString();
                age = binding.age.getText().toString();

                putData(name, age);

                Fragment fragment = new GenderFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_left,
                        R.anim.slide_out_left);

                fragmentTransaction.replace(R.id.container, fragment).addToBackStack("").commitAllowingStateLoss();
                // startActivity(new Intent(getActivity(), ProfileActivity.class));

            }
        });


    }

    private void putData(String name, String age) {

        queryPreferences.setName(name, age);

    }

}
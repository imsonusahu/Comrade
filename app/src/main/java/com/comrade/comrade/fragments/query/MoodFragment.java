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
import com.comrade.comrade.databinding.FragmentMoodBinding;

public class MoodFragment extends Fragment {


    FragmentMoodBinding binding;
    private String mood;

    QueryPreferences queryPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mood, container, false);

        queryPreferences = new QueryPreferences(getActivity());
        TextView textView = getActivity().findViewById(R.id.titleBase);
        textView.setText("Mood");

        onClick();

        return binding.getRoot();


    }

    private void onClick() {

        RadioGroup rg = binding.getRoot().findViewById(R.id.moodRadioGroup);

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.readyToChat:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Ready to chat", Toast.LENGTH_SHORT).show();
                    mood = "Ready to chat";
                    break;

                case R.id.lookingToMeet:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Looking to meet up", Toast.LENGTH_SHORT).show();
                    mood = "Looking to meet up";
                    break;

                case R.id.hungry:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Hungry. Let's eat!", Toast.LENGTH_SHORT).show();
                    mood = "Hungry. Let's eat!";
                    break;

                case R.id.bored:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Bored. Message me!", Toast.LENGTH_SHORT).show();
                    mood = "Bored. Message me!";
                    break;

                case R.id.cravingCoffee:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Craving coffee", Toast.LENGTH_SHORT).show();
                    mood = "Craving coffee";
                    break;

                case R.id.dinner:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Ready for dinner", Toast.LENGTH_SHORT).show();
                    mood = "Ready for dinner";
                    break;

                case R.id.drink:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "I could use a drink", Toast.LENGTH_SHORT).show();
                    mood = "I could use a drink";
                    break;

                case R.id.laugh:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "Make me laugh", Toast.LENGTH_SHORT).show();
                    mood = "Make me laugh";

                    break;

                case R.id.notSay:
                    // do operations specific to this selection
                    Toast.makeText(getContext(), "I'd rather than say", Toast.LENGTH_SHORT).show();
                    mood = "I'd rather than say";
                    break;

            }
        });


        Button nextButton = getActivity().findViewById(R.id.myBtnNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new TellUsAboutYouFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_left,
                        R.anim.slide_out_left);

                setData(mood);

                fragmentTransaction.replace(R.id.container, fragment).addToBackStack("")
                        .commitAllowingStateLoss();
                // startActivity(new Intent(getActivity(), ProfileActivity.class));

            }
        });


    }

    private void setData(String mood) {

        queryPreferences.setShareMood(mood);

    }
}
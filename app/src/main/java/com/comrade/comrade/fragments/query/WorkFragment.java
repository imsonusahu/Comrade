package com.comrade.comrade.fragments.query;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.activity.ProfileActivity;
import com.comrade.comrade.databinding.FragmentWorkBinding;


public class WorkFragment extends Fragment {


    FragmentWorkBinding binding;

    QueryPreferences queryPreferences;
    String jobTitle,companyName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_work, container, false);


        queryPreferences=new QueryPreferences(getActivity());


        TextView textView= getActivity().findViewById(R.id.titleBase);
        textView.setText("Job");
        onClick();





        return binding.getRoot();
    }

    private void onClick() {


        Button nextButton = getActivity().findViewById(R.id.myBtnNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jobTitle=binding.jobTitle.getText().toString();
                companyName=binding.companyName.getText().toString();

                setData(jobTitle,companyName);
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });
    }

    private void setData(String jobTitle, String companyName) {

        queryPreferences.doYouWork(jobTitle,companyName);
    }


}
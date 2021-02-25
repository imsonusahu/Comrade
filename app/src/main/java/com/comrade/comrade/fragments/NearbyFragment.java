package com.comrade.comrade.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.comrade.comrade.R;
import com.comrade.comrade.activity.FilterActivity;
import com.comrade.comrade.adapters.NearByProfileAdapter;
import com.comrade.comrade.adapters.NearByStoryAdapter;
import com.comrade.comrade.databinding.FragmentNearbyBinding;
import com.comrade.comrade.models.NearByProfileModel;
import com.comrade.comrade.models.StoryProfileModel;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;


public class NearbyFragment extends Fragment {


    FragmentNearbyBinding binding;
    ArrayList<StoryProfileModel> storyArrayList = new ArrayList<>();
    ArrayList<NearByProfileModel> nearByProfileModels = new ArrayList<>();

    NearByStoryAdapter adapter;

    NearByProfileAdapter nearByProfileAdapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nearby, container, false);


        onClick();
        getDataNearby();

        return binding.getRoot();
    }

    private void getDataNearby() {






    }

    private void onClick() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvStory.setLayoutManager(layoutManager);
        adapter = new NearByStoryAdapter(getActivity(), storyArrayList);
        binding.rvStory.setHasFixedSize(true);
        binding.rvStory.setItemViewCacheSize(20);
        binding.rvStory.setDrawingCacheEnabled(true);
        binding.rvStory.setAdapter(adapter);

        binding.rvNearbyProfile.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        nearByProfileAdapter = new NearByProfileAdapter(getActivity(), nearByProfileModels);
        binding.rvNearbyProfile.setHasFixedSize(true);
        binding.rvNearbyProfile.setItemViewCacheSize(20);
        binding.rvNearbyProfile.setDrawingCacheEnabled(true);
        binding.rvNearbyProfile.setAdapter(nearByProfileAdapter);

        binding.filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), FilterActivity.class));
            }
        });

    }
}
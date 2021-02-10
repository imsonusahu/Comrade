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

import java.util.ArrayList;


public class NearbyFragment extends Fragment {


    FragmentNearbyBinding binding;
    ArrayList<StoryProfileModel> storyArrayList = new ArrayList<>();
    ArrayList<NearByProfileModel> nearByProfileModels = new ArrayList<>();

    NearByStoryAdapter adapter;

    NearByProfileAdapter nearByProfileAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nearby, container, false);


        onClick();


        if (storyArrayList != null) {
            storyArrayList.add(new StoryProfileModel("1", "https://i.pinimg.com/originals/7d/21/f4/7d21f47169e4276a368266329abf8c79.jpg"));
            storyArrayList.add(new StoryProfileModel("2", "https://assets.vogue.com/photos/58dedea1c83a5e7c386dc9bc/master/pass/00-square-scarlett-johansson-5-things.jpg"));
            storyArrayList.add(new StoryProfileModel("3", "https://static.jobscan.co/blog/uploads/linkedin-profile-picture-1280x720.jpg"));
            storyArrayList.add(new StoryProfileModel("4", "https://www.celebsfacts.com/wp-content/uploads/2019/09/Katherine-Langford.jpg"));

        }

        if (nearByProfileModels != null) {
            nearByProfileModels.add(new NearByProfileModel("1", getString(R.string.priyanka_tik_tok), "Priyanka", "24", true));
            nearByProfileModels.add(new NearByProfileModel("2", "https://assets.vogue.com/photos/58dedea1c83a5e7c386dc9bc/master/pass/00-square-scarlett-johansson-5-things.jpg", "Scarlett Johansson", "32", true));
            nearByProfileModels.add(new NearByProfileModel("3", "https://static.jobscan.co/blog/uploads/linkedin-profile-picture-1280x720.jpg", "Kathrin", "26", true));
            nearByProfileModels.add(new NearByProfileModel("4", "https://www.celebsfacts.com/wp-content/uploads/2019/09/Katherine-Langford.jpg", "Kathrin", "26", true));
            nearByProfileModels.add(new NearByProfileModel("5", "https://awesomehindi.com/wp-content/uploads/2019/04/beautyfull-girls-profile-picture.jpg", "Neeto Singh", "20", false));
            nearByProfileModels.add(new NearByProfileModel("6", "https://www.goodmorningimagesforlover.com/wp-content/uploads/2018/09/pics-for-facebook-profile-female.jpg", "Kiran chandel", "23", true));
            nearByProfileModels.add(new NearByProfileModel("7", "https://i1.wp.com/bloggers.society19.com/wp-content/uploads/2015/11/tinder-girls.jpg?resize=550%2C687&ssl=1", "Rajni", "26", true));
            nearByProfileModels.add(new NearByProfileModel("8", "https://www.celebsfacts.com/wp-content/uploads/2019/09/Katherine-Langford.jpg", "Kathrin", "26", true));


        }


        return binding.getRoot();
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
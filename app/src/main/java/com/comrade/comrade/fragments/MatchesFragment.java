package com.comrade.comrade.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.comrade.comrade.R;
import com.comrade.comrade.adapters.menu.WhoLikedAdapter;
import com.comrade.comrade.databinding.FragmentMachesBinding;
import com.comrade.comrade.models.UsersListChatModel;

import java.util.ArrayList;


public class MatchesFragment extends Fragment {


    FragmentMachesBinding binding;

    ArrayList<UsersListChatModel> arrayList=new ArrayList<>();
    WhoLikedAdapter whoLikedAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_maches, container, false);



        viewInit();




        return binding.getRoot();
    }

    private void viewInit() {

        binding.matchRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.matchRv.setHasFixedSize(true);
        binding.matchRv.setItemViewCacheSize(arrayList.size());




    }
}
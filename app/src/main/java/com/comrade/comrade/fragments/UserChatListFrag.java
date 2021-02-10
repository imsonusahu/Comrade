package com.comrade.comrade.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.comrade.comrade.R;
import com.comrade.comrade.adapters.UserChatListAdapter;
import com.comrade.comrade.databinding.FragmentUserChatListBinding;
import com.comrade.comrade.models.UsersListChatModel;

import java.util.ArrayList;


public class UserChatListFrag extends Fragment {


    private ArrayList<UsersListChatModel> arrayList = new ArrayList<>();


    FragmentUserChatListBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_chat_list, container, false);


        arrayList.add(new UsersListChatModel("2",
                "Katherin langford",
                "Hey!",
                "7:00pm",
                "https://i.dailymail.co.uk/1s/2020/07/18/01/30900068-8535649-image-a-1_1595031679972.jpg", true));

        arrayList.add(new UsersListChatModel("2",
                "Natasha Romanof",
                "How are you!",
                "5:00pm",
                "https://i.pinimg.com/474x/d7/ca/f8/d7caf862a1166f24c2ee3b5d4f3897f7--red-hair-girls-marvel-films.jpg", true));


        initView();


        return binding.getRoot();

    }

    private void initView() {

        binding.rvUserMsgList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvUserMsgList.setItemViewCacheSize(20);
        UserChatListAdapter adapter = new UserChatListAdapter(getActivity(), arrayList);
        binding.rvUserMsgList.setAdapter(adapter);

    }
}
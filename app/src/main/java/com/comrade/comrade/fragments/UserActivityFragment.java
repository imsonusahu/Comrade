package com.comrade.comrade.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.comrade.comrade.R;
import com.comrade.comrade.databinding.FragmentUserActivityBinding;
import com.comrade.comrade.models.UsersListChatModel;

import java.util.ArrayList;



public class UserActivityFragment extends Fragment {





    FragmentUserActivityBinding binding;
    private ArrayList<UsersListChatModel> arrayList=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_user_activity, container, false);


/*

        arrayList.add(new UsersListChatModel("2",
                "Katherin langford",
                "Hey!",
                "7:00pm",
                "https://i.dailymail.co.uk/1s/2020/07/18/01/30900068-8535649-image-a-1_1595031679972.jpg",true));

        arrayList.add(new UsersListChatModel("2",
                "Natasha Romanof",
                "How are you!",
                "5:00pm",
                "https://i.pinimg.com/474x/d7/ca/f8/d7caf862a1166f24c2ee3b5d4f3897f7--red-hair-girls-marvel-films.jpg",true));

*/


        initView();


        return binding.getRoot();

    }

    private void initView() {

       /* binding.rvUserMsgList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvUserMsgList.setItemViewCacheSize(20);
        UserChatListAdapter adapter=new UserChatListAdapter(getActivity(),arrayList);
        binding.rvUserMsgList.setAdapter(adapter);
*/
    }
}
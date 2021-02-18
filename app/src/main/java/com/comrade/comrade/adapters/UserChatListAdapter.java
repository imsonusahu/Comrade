package com.comrade.comrade.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.comrade.comrade.R;
import com.comrade.comrade.activity.ChatActivity;
import com.comrade.comrade.models.UsersListChatModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserChatListAdapter extends RecyclerView.Adapter<UserChatListAdapter.VH> {

    ArrayList<UsersListChatModel> arrayList;
    Context context;

    public UserChatListAdapter(FragmentActivity activity, ArrayList<UsersListChatModel> myArraylist) {

        context = activity;
        arrayList = myArraylist;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_msg_list, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserChatListAdapter.VH holder, int position) {

        UsersListChatModel chatModel = arrayList.get(position);

        holder.userName.setText(chatModel.getUserName());
        String roomId=chatModel.getRoomId();



        try {
            Glide.with(context)
                    .load(chatModel.getProfilePic())
                    .centerCrop()
                    .into(holder.imageView);
        } catch (Exception e) {

            e.printStackTrace();
            Log.e("adapter", e.getMessage());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("name", chatModel.getUserName());
                intent.putExtra("pic", chatModel.getProfilePic());
                intent.putExtra("matchId", chatModel.getMatchId());
                intent.putExtra("roomId", chatModel.getMatchId());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;
    }

    public static class VH extends RecyclerView.ViewHolder {
        CircleImageView imageView, activeStatus;
        TextView userName, lastMsg, msgTime;

        public VH(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.userProfileChatList);
            userName = itemView.findViewById(R.id.userNameChatList);
            lastMsg = itemView.findViewById(R.id.lastMsg);
            msgTime = itemView.findViewById(R.id.msgTime);
            activeStatus = itemView.findViewById(R.id.activeStatus2);

        }
    }
}

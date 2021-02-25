package com.comrade.comrade.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.comrade.comrade.R;
import com.comrade.comrade.activity.ChatActivity;
import com.comrade.comrade.chats.MessageFormat;

import java.util.List;

public class MessageAdapter extends ArrayAdapter< MessageFormat > {
    public MessageAdapter(Context context, int resource, List<MessageFormat> objects) {
        super(context, resource, objects);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(ChatActivity.TAG, "getView:");

        MessageFormat message = getItem(position);


        String senderId=message.getSenderId();

        if(TextUtils.isEmpty(message.getMessage())){


            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.user_connected, parent, false);

            TextView messageText = convertView.findViewById(R.id.message_body);

            Log.i(ChatActivity.TAG, "getView: is empty ");
            String userConnected = message.getUsername();
            messageText.setText(userConnected);

        }else if(message.getUniqueId().equals(ChatActivity.myId)){
            Log.i(ChatActivity.TAG, "getView: " + message.getUniqueId() + " " + ChatActivity.myId);

            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.my_message, parent, false);
            TextView messageText = convertView.findViewById(R.id.message_body);
            messageText.setText(message.getMessage());
            notifyDataSetChanged();

        }else{
            Log.i(ChatActivity.TAG, "getView: is not empty");

            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.their_message, parent, false);

            TextView messageText = convertView.findViewById(R.id.message_body);
            TextView usernameText = (TextView) convertView.findViewById(R.id.name);

            messageText.setVisibility(View.VISIBLE);
            usernameText.setVisibility(View.VISIBLE);

            messageText.setText(message.getMessage());
            usernameText.setText(message.getUsername());
            notifyDataSetChanged();
        }

        return convertView;
    }
}

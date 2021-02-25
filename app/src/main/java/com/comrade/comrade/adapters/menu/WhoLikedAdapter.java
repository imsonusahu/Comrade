package com.comrade.comrade.adapters.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.activity.ChatActivity;
import com.comrade.comrade.models.UsersListChatModel;
import com.comrade.comrade.models.WhoLikedModel;
import com.comrade.comrade.volly.Variables;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class WhoLikedAdapter extends RecyclerView.Adapter<WhoLikedAdapter.VH> {

    private static final String TAG = "Who like adapter";
    ArrayList<WhoLikedModel> arrayList;
    Context context;
    boolean isActive;

    private String matchUserId, roomId, myId;

    private String msg;

    WhoLikedModel matchModel;
    ArrayList<UsersListChatModel> userChatList;

    QueryPreferences queryPreferences;
    HashMap<String, String> users;

    public WhoLikedAdapter(FragmentActivity activity, ArrayList<WhoLikedModel> myArraylist) {

        context = activity;
        arrayList = myArraylist;
    }

    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.who_liked_ui, parent, false);
        return new VH(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull WhoLikedAdapter.VH holder, int position) {

        matchModel = arrayList.get(position);
        queryPreferences = new QueryPreferences(context);
        users = queryPreferences.getUserDetail();

        holder.name_age.setText(matchModel.getUsername() + ", " + matchModel.getDob() + "");
        holder.location_dist.setText(matchModel.getDistance() + ", " + matchModel.getDistance());


        try {
            Glide.with(context)
                    .load(matchModel.getProfilePic())
                    .centerCrop()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(holder.matchUserImg);

            Glide.with(context)
                    .load(matchModel.getProfilePic())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .skipMemoryCache(true)
                    .into(holder.userMatchBg);


            Glide.with(context)
                    .load("https://i.pinimg.com/originals/c5/59/77/c559778bc16bff7d7d459c65154b0d9e.jpg")
                    .centerCrop()
                    .skipMemoryCache(true)
                    .into(holder.currentUserPic);


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("WhoLikeAdapter",e.getMessage());
        }
        holder.letsChatBtn.setOnClickListener(v -> {


            Bundle bundle = new Bundle();

            Intent intent = new Intent(context, ChatActivity.class);
            bundle.putString("roomId", arrayList.get(position).getMatchId());
            bundle.putString("myId", users.get(queryPreferences.uid));
            bundle.putString("matchUserId", arrayList.get(position).get_id());
            bundle.putString("name", arrayList.get(position).getUsername());
            bundle.putString("pic", arrayList.get(position).getProfilePic());

            queryPreferences.setIsChat("isChat");

            intent.putExtras(bundle);

            context.startActivity(intent);


        });


    }

    private void createChat(String roomId, String myId, String msg, String currentUid) {

        userChatList = new ArrayList<>();


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("msg_by_id", myId);
            jsonObject.put("msg_to_id", currentUid);
            jsonObject.put("message", msg);


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("json", e.getMessage());
        }

        final String mRequestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Variables.CREATE_CHAT, response -> {
            Log.e("LOG_VOLLEY", "Create chat : " + response);


            try {
                JSONObject object = new JSONObject(response);

                userChatList.add(new UsersListChatModel(users.get(queryPreferences.uid),
                        object.getString("msg_to_id"), object.getString("msg_by_id"),
                        matchModel.getProfilePic(), "_id", matchModel.getUsername()));

            } catch (JSONException jsonException) {
                jsonException.printStackTrace();

                Log.e("WhoLiked", "liked_to Error : " + jsonException.getMessage());
            }
        }, error -> Log.e("LOG_VOLLEY", error.toString())) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                return mRequestBody.getBytes(StandardCharsets.UTF_8);
            }
        };

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) {

                Log.e(TAG, "Time error volley : " + error.getMessage());
            }
        });
        stringRequest.setShouldCache(false);
        Volley.newRequestQueue(context).add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;
    }

    public void updates(ArrayList<WhoLikedModel> result) {

        arrayList = new ArrayList<>();
        arrayList.addAll(result);
        notifyDataSetChanged();
    }

    public static class VH extends RecyclerView.ViewHolder {

        ImageView userMatchBg;
        CircleImageView matchUserImg, currentUserPic;
        TextView name_age, location_dist;

        Button letsChatBtn;

        public VH(View itemView) {
            super(itemView);

            userMatchBg = itemView.findViewById(R.id.userMatchBg);
            name_age = itemView.findViewById(R.id.matchName);
            location_dist = itemView.findViewById(R.id.location_dist);
            matchUserImg = itemView.findViewById(R.id.matchUserImg);
            currentUserPic = itemView.findViewById(R.id.currentUserPic);
            letsChatBtn = itemView.findViewById(R.id.sendMsgButtonMatch);


        }
    }
}

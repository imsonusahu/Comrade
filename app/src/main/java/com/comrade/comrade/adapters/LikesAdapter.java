package com.comrade.comrade.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.comrade.comrade.R;
import com.comrade.comrade.models.StoryProfileModel;

import java.util.ArrayList;

public class LikesAdapter extends RecyclerView.Adapter<LikesAdapter.VH> {

    ArrayList<StoryProfileModel> arrayList;
    Context context;

    public LikesAdapter(FragmentActivity activity, ArrayList<StoryProfileModel> myArraylist) {

        context = activity;
        arrayList = myArraylist;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_profiles, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikesAdapter.VH holder, int position) {

        StoryProfileModel imageModel = arrayList.get(position);

        try {
            Glide.with(context)
                    .load(imageModel.getProfilePic())
                    .centerCrop()
                    .into(holder.imageView);
        } catch (Exception e) {

            e.printStackTrace();
            Log.e("adapter", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;
    }

    public static class VH extends RecyclerView.ViewHolder {
        ImageView imageView;


        public VH(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.storyProfileImg);


        }
    }
}

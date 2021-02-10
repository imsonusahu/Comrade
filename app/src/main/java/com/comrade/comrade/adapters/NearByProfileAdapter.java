package com.comrade.comrade.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.comrade.comrade.R;
import com.comrade.comrade.activity.UserProfileActivity;
import com.comrade.comrade.models.NearByProfileModel;

import java.util.ArrayList;

public class NearByProfileAdapter extends RecyclerView.Adapter<NearByProfileAdapter.VH> {

    ArrayList<NearByProfileModel> arrayList;
    Context context;

    boolean isActive;

    public NearByProfileAdapter(FragmentActivity activity, ArrayList<NearByProfileModel> myArraylist) {

        context = activity;
        arrayList = myArraylist;

    }

    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearby_profiles, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NearByProfileAdapter.VH holder, int position) {

        NearByProfileModel imageModel = arrayList.get(position);


        String age = imageModel.getAge();
        holder.name.setText(imageModel.getUsername() + ", " + age);

        isActive = imageModel.isActiveStatus();

        if (isActive == true) {


            holder.activeHint.setVisibility(View.VISIBLE);

        } else {
            holder.activeHint.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, UserProfileActivity.class));
            }
        });


/*
        ImageView imageView=((AppCompatActivity)context).findViewById(R.id.storyProfileImg);


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Glide.with(context)
                            .load("https://www.thisiscolossal.com/wp-content/uploads/2014/03/120430.gif")
                            .centerCrop()
                            .into(imageView);


                } catch (Exception e) {

                    e.printStackTrace();
                    Log.e("adapter", e.getMessage());
                }
            }
        });
*/


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

        ImageView imageView, activeHint;
        TextView name;


        public VH(View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.nearByProfile);
            name = itemView.findViewById(R.id.nearByProfileName);
            activeHint = itemView.findViewById(R.id.activeStatus);

        }
    }
}
